package edu.java.scrapper.clients;

import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.client.impl.StackOverflowClientImpl;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class StackOverflowClientTest extends AbstractWiremockTest {

    @Test
    @DisplayName("Тестирование получения ответов по идентификатору вопроса")
    public void testFetchAnswersByQuestionId() {
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl);
        long questionId = 67890;
        OffsetDateTime creationDate = OffsetDateTime.now(ZoneOffset.UTC);
        String responseBody = String.format("""
            {
                "items": [
                    {
                        "owner": {
                            "display_name": "New User"
                        },
                        "creation_date": "%s",
                        "question_id": %d
                    }
                ]
            }
            """, creationDate, questionId);

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
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl);

        long questionId = 123;
        wireMockServer.stubFor(get(urlEqualTo(
            "/questions/" + questionId + "/answers?order=desc&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(404)));

        assertThrows(WebClientResponseException.class, () -> stackOverflowClient.fetchAnswersByQuestionId(questionId));
    }
}
