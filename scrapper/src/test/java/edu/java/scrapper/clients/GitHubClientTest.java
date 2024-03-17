package edu.java.scrapper.clients;

import edu.java.scrapper.dto.github.GitHubResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class GitHubClientTest extends WireMockBaseTest {

    @Test
    @DisplayName("Получение информации о репозитории")
    void fetchRepositoryInfoTest() {
        // Arrange
        String jsonResponse = """
            {
              "owner": {
                "login": "owner-name",
                "id": 123
              },
              "full_name": "owner-name/repo-name",
              "pushed_at": "2020-01-01T01:01:01Z"
            }""";

        wireMockServer.stubFor(get(urlEqualTo("/repos/your-github-owner/your-repo-name"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(jsonResponse)
                .withStatus(200)));

        GitHubClient gitHubClient = new GitHubClientImpl(webClient);

        // Act
        Mono<GitHubResponse> responseMono = gitHubClient.fetchRepositoryInfo("your-github-owner", "your-repo-name");

        // Assert
        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.getFullName().equals("owner-name/repo-name"))
            .verifyComplete();
    }
}
