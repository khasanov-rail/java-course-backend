package edu.java.scrapper.clients;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.impl.GitHubClientImpl;
import edu.java.scrapper.dto.github.GitHubDTO;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@ExtendWith(MockitoExtension.class)
public class GitHubClientTest extends AbstractWiremockTest {

    @Test
    @DisplayName("Тестирование получения данных о репозитории")
    public void testFetchRepo() {
        GitHubClient gitHubClient = new GitHubClientImpl(baseUrl);

        String owner = "testOwner";
        String repo = "testRepo";
        String fullName = "testOwner/testRepo";
        String pushedAt = "2024-01-01T12:00:00Z";
        String responseBody = """
            {
              "owner": {
                "login": "%s",
                "id": 123
              },
              "full_name": "%s",
              "pushed_at": "%s"
            }
            """.formatted(owner, fullName, pushedAt);

        wireMockServer.stubFor(get(urlEqualTo("/repos/" + owner + "/" + repo))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        GitHubDTO gitHubDTO = gitHubClient.fetchRepo(owner, repo);

        Assertions.assertEquals(owner, gitHubDTO.owner().login());
        Assertions.assertEquals(fullName, gitHubDTO.fullName());
        Assertions.assertEquals(OffsetDateTime.parse(pushedAt), gitHubDTO.pushedAt());
    }

    @Test
    @DisplayName("Тестирование обработки ошибки 404 при запросе данных о репозитории")
    public void testFetchRepo_NotFound() {
        GitHubClient gitHubClient = new GitHubClientImpl(baseUrl);

        String owner = "testOwnerNumberOne";
        String repo = "testRepoNumberOne";

        wireMockServer.stubFor(get(urlEqualTo("/repos/" + owner + "/" + repo))
            .willReturn(aResponse()
                .withStatus(404)));

        Assertions.assertThrows(WebClientResponseException.class, () -> gitHubClient.fetchRepo(owner, repo));
    }
}
