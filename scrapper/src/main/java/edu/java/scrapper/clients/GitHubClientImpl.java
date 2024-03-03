package edu.java.scrapper.clients;

import edu.java.scrapper.dto.github.GitHubResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubClientImpl implements GitHubClient {

    private final WebClient webClient;

    public GitHubClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<GitHubResponse> fetchRepositoryInfo(String owner, String repoName) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repoName)
            .retrieve()
            .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse -> Mono.error(new RuntimeException("Error response from GitHub API")))
            .bodyToMono(GitHubResponse.class);
    }
}
