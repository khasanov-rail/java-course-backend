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
    @DisplayName("Тестирование получения данных о вопросе")
    public void testFetchQuestion() {
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl);
        long questionId = 67890;
        OffsetDateTime lastActivityDate = OffsetDateTime.now(ZoneOffset.UTC);
        long answerCount = 10;
        String responseBody = """
            {
                "items": [
                    {
                        "owner": {
                            "account_id": 98765,
                            "display_name": "New User"
                        },
                        "last_activity_date": "%s",
                        "question_id": %d,
                        "answer_count": %d
                    }
                ]
            }
            """.formatted(lastActivityDate, questionId, answerCount);

        wireMockServer.stubFor(get(urlEqualTo(
            "/questions/" + questionId + "?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        StackOverflowDTO stackOverflowDTO = stackOverflowClient.fetchQuestion(questionId);

        assertEquals(1, stackOverflowDTO.items().size());
        StackOverflowDTO.Item item = stackOverflowDTO.items().get(0);
        assertEquals(questionId, item.questionId());
        assertEquals(answerCount, item.answerCount());
        assertEquals(lastActivityDate.withOffsetSameInstant(ZoneOffset.UTC), item.lastActivityDate());
        assertEquals(98765, item.owner().accountId());
        assertEquals("New User", item.owner().displayName());
    }

    @Test
    @DisplayName("Тестирование обработки ошибки 404 для нового вопроса")
    public void testFetchQuestion_NotFound() {
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl);

        long questionId = 123;
        wireMockServer.stubFor(get(urlEqualTo(
            "/questions/" + questionId + "?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(404)));

        assertThrows(WebClientResponseException.class, () -> stackOverflowClient.fetchQuestion(questionId));
    }
}
