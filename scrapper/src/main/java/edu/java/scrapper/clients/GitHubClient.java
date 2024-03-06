package edu.java.scrapper.clients;

import edu.java.scrapper.dto.github.GitHubResponse;
import reactor.core.publisher.Mono;

public interface GitHubClient {
    Mono<GitHubResponse> fetchRepositoryInfo(String owner, String repoName);
}
