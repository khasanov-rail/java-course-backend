package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.impl.GitHubClientImpl;
import edu.java.scrapper.configuration.retry.RetryProperties;
import edu.java.scrapper.dto.github.GitHubDTO;
import edu.java.scrapper.exception.custom.ResourceUnavailableException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GitHubClientTest {
    private static WireMockServer wireMockServer;
    private static String baseUrl;
    private static RetryProperties retryProperties;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(3000);
        wireMockServer.start();
        baseUrl = "http://localhost:" + wireMockServer.port();
        RetryBackoffSpec retry = Retry.backoff(5, Duration.ofMillis(10))
            .filter(throwable -> throwable instanceof ResourceUnavailableException ||
                throwable instanceof WebClientRequestException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                throw new ResourceUnavailableException("Try later", HttpStatus.SERVICE_UNAVAILABLE);
            });
        retryProperties = new RetryProperties(List.of(HttpStatus.FORBIDDEN), retry);
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Успешное получение данных из репозитория")
    public void testFetchRepo() {
        GitHubClient gitHubClient = new GitHubClientImpl(baseUrl, retryProperties);

        String owner = "testOwner";
        String repo = "testRepo";
        String fullName = "testOwner/testRepo";
        String pushedAt = "2024-01-01T12:00:00Z";
        String responseBody = String.format(
            """
                {
                 "actor": {"login": "%s"},
                 "type": "PushEvent",
                 "repo": {"url": "%s"},
                 "created_at": "%s",
                 "payload": {
                    "commits": [{"message": "Initial commit"}],
                    "ref": "main",
                    "action": "push",
                    "pull_request": {"title": "Merge pull request #1 from feature/new-feature"},
                    "issue": {"title": "Fix bug #123"}
                 }
                }""",
            owner,
            fullName,
            pushedAt
        );

        wireMockServer.stubFor(get(urlEqualTo("/repos/" + owner + "/" + repo + "/events"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        List<GitHubDTO> gitHubDTO = gitHubClient.fetchRepo(owner, repo);

        Assertions.assertEquals(owner, gitHubDTO.get(0).actor().login());
        Assertions.assertEquals(fullName, gitHubDTO.get(0).repo().url());
        Assertions.assertEquals(OffsetDateTime.parse(pushedAt), gitHubDTO.get(0).createdAt());
    }

    @Test
    @DisplayName("Обработка ошибки 404 при запросе к репозиторию")
    public void testFetchRepo_NotFound() {
        GitHubClient gitHubClient = new GitHubClientImpl(baseUrl, retryProperties);

        String owner = "testOwnerNumberOne";
        String repo = "testRepoNumberOne";

        wireMockServer.stubFor(get(urlEqualTo("/repos/" + owner + "/" + repo))
            .willReturn(aResponse()
                .withStatus(404)));

        assertThrows(WebClientResponseException.class, () -> gitHubClient.fetchRepo(owner, repo));
    }
}
