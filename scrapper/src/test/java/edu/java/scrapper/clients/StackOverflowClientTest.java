package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.client.impl.StackOverflowClientImpl;
import edu.java.scrapper.configuration.retry.RetryProperties;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import edu.java.scrapper.exception.custom.ResourceUnavailableException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class StackOverflowClientTest {
    private static WireMockServer wireMockServer;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(3003);
        wireMockServer.start();
        baseUrl = "http://localhost:" + wireMockServer.port();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Тестирование получения ответов по идентификатору вопроса")
    public void testFetchAnswersByQuestionId() {
        RetryBackoffSpec retrySpec = Retry.backoff(5, Duration.ofSeconds(1))
            .filter(e -> e instanceof WebClientResponseException.NotFound)
            .onRetryExhaustedThrow((spec, signal) -> new WebClientResponseException(
                "404 NOT FOUND", 404, "Not Found", null, null, null));
        RetryProperties retryProperties = new RetryProperties(Arrays.asList(HttpStatus.NOT_FOUND), retrySpec);

        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl, retryProperties);
        long questionId = 67890;
        OffsetDateTime creationDate = OffsetDateTime.now(ZoneOffset.UTC);
        String responseBody = """
            {
                "items": [
                    {
                        "owner": {"display_name": "New User"},
                        "creation_date": "%s",
                        "question_id": %d
                    }
                ]
            }
            """.formatted(creationDate, questionId);

        wireMockServer.stubFor(get(urlEqualTo(
            "/questions/" + questionId + "/answers?order=desc&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        StackOverflowDTO stackOverflowDTO = stackOverflowClient.fetchAnswersByQuestionId(questionId);

        assertEquals(1, stackOverflowDTO.items().size());
        StackOverflowDTO.Item item = stackOverflowDTO.items().get(0);
        assertEquals(questionId, item.questionId());
        assertEquals(creationDate.withOffsetSameInstant(ZoneOffset.UTC), item.creationDate());
        assertEquals("New User", item.owner().displayName());
    }

    @Test
    @DisplayName("Тестирование обработки ошибки 404 при запросе ответов")
    public void testFetchAnswersByQuestionId_NotFound() {
        RetryBackoffSpec retrySpec = Retry.backoff(5, Duration.ofMillis(100))
            .filter(e -> e instanceof WebClientResponseException.NotFound)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                return new ResourceUnavailableException("Try later", HttpStatus.NOT_FOUND);
            });

        StackOverflowClient stackOverflowClient =
            new StackOverflowClientImpl(baseUrl, new RetryProperties(Arrays.asList(HttpStatus.NOT_FOUND), retrySpec));

        long questionId = 123;
        wireMockServer.stubFor(get(urlEqualTo("/questions/" + questionId + "/answers?order=desc&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(404)));

        assertThrows(
            ResourceUnavailableException.class,
            () -> stackOverflowClient.fetchAnswersByQuestionId(questionId)
        );
    }
}
