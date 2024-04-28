package edu.java.scrapper.clients;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.impl.GitHubClientImpl;
import edu.java.scrapper.dto.github.GitHubDTO;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GitHubClientTest extends AbstractWiremockTest {

    @Test
    @DisplayName("Тестирование получения списка событий из репозитория")
    public void testFetchRepo() {
        GitHubClient gitHubClient = new GitHubClientImpl(baseUrl);

        String owner = "testOwner";
        String repo = "testRepo";
        String fullName = "testOwner/testRepo";
        String createdAt = "2024-01-01T12:00:00Z";
        String responseBody = """
            [{
              "actor": {
                "login": "%s",
                "id": 123
              },
              "repo": {
                "url": "%s"
              },
              "created_at": "%s",
              "event_type": "PushEvent"
            }]
            """.formatted(owner, fullName, createdAt);

        wireMockServer.stubFor(get(urlEqualTo("/repos/" + owner + "/" + repo + "/events"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        List<GitHubDTO> gitHubDTOs = gitHubClient.fetchRepo(owner, repo);

        Assertions.assertEquals(owner, gitHubDTOs.get(0).actor().login());
        Assertions.assertEquals(fullName, gitHubDTOs.get(0).repo().url());
        Assertions.assertEquals(OffsetDateTime.parse(createdAt), gitHubDTOs.get(0).createdAt());
    }

    @Test
    @DisplayName("Тестирование обработки ошибки 404 при запросе списка событий из репозитория")
    public void testFetchRepo_NotFound() {
        GitHubClient gitHubClient = new GitHubClientImpl(baseUrl);

        String owner = "testOwnerNumberOne";
        String repo = "testRepoNumberOne";

        wireMockServer.stubFor(get(urlEqualTo("/repos/" + owner + "/" + repo + "/events"))
            .willReturn(aResponse()
                .withStatus(404)));

        assertThrows(WebClientResponseException.class, () -> gitHubClient.fetchRepo(owner, repo));
    }
}
