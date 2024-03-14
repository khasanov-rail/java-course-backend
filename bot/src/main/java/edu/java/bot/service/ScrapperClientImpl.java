package edu.java.bot.service;

import edu.java.bot.dto.response.GitHubInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ScrapperClientImpl implements ScrapperClient {
    private final WebClient webClient;

    public ScrapperClientImpl(@Value("${scrapper.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Override
    public Mono<GitHubInfoResponse> fetchGitHubInfo(String owner, String repoName) {
        return webClient.get()
            .uri("/github/repository-info?owner={owner}&repo={repoName}", owner, repoName)
            .retrieve()
            .bodyToMono(GitHubInfoResponse.class);
    }
}
