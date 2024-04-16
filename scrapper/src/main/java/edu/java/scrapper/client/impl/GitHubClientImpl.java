package edu.java.scrapper.client.impl;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.dto.github.GitHubDTO;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    public GitHubClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GitHubDTO fetchRepo(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(GitHubDTO.class)
            .block();
    }
}
