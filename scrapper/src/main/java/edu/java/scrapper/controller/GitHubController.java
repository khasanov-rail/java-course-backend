package edu.java.scrapper.controller;

import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.dto.github.GitHubResponse;
import edu.java.scrapper.exception.customExeptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GitHubController {

    private final GitHubClient gitHubClient;

    public GitHubController(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @GetMapping("/github/repository-info")
    public Mono<ResponseEntity<GitHubResponse>> getRepositoryInfo(
        @RequestParam String owner,
        @RequestParam String repo
    ) {
        return gitHubClient.fetchRepositoryInfo(owner, repo)
            .map(ResponseEntity::ok)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(
                "Repository not found for owner: " + owner + " and repo: " + repo)));
    }
}
