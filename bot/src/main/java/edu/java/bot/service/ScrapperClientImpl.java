package edu.java.bot.service;

import edu.java.bot.dto.request.GitHubInfoRequest;
import edu.java.bot.dto.request.StackOverflowInfoRequest;
import edu.java.bot.dto.response.GitHubInfoResponse;
import edu.java.bot.dto.response.StackOverflowInfoResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClientImpl implements ScrapperClient {
    private final WebClient webClient;

    public ScrapperClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<GitHubInfoResponse> fetchGitHubInfo(GitHubInfoRequest request) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/github/repository-info")
                .queryParam("owner", request.getOwner())
                .queryParam("repoName", request.getRepoName())
                .build())
            .retrieve()
            .bodyToMono(GitHubInfoResponse.class);
    }

    @Override
    public Mono<StackOverflowInfoResponse> fetchStackOverflowInfo(StackOverflowInfoRequest request) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/stackoverflow/questions")
                .queryParam("tag", request.getTag())
                .build())
            .retrieve()
            .bodyToMono(StackOverflowInfoResponse.class);
    }
}
