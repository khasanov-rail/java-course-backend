package edu.java.scrapper.clients;

import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class StackOverflowClientTest extends WireMockBaseTest {

    @Test
    @DisplayName("Получение вопросов с StackOverflow")
    void fetchQuestionsTest() {
        // Arrange
        String jsonResponse = "{\"items\": [{\"owner\": {\"account_id\": 123, \"display_name\": \"user-name\"}, \"last_activity_date\": \"2020-01-01T01:01:01Z\", \"question_id\": 1234567, \"title\": \"Test Question\", \"answer_count\": 42}]}";
        wireMockServer.stubFor(get(urlPathEqualTo("/2.3/questions"))
            .withQueryParam("order", equalTo("desc"))
            .withQueryParam("sort", equalTo("activity"))
            .withQueryParam("site", equalTo("stackoverflow"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(jsonResponse)
                .withStatus(200)));

        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(webClient);

        // Act
        Mono<StackOverflowDTO> responseMono = stackOverflowClient.fetchQuestions("desc", "activity", "stackoverflow");

        // Assert
        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.items().get(0).title().equals("Test Question"))
            .verifyComplete();
    }
}
