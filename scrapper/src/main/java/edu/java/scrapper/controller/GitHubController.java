package edu.java.scrapper.controller;

import edu.java.scrapper.dto.github.GitHubResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubController {

    @GetMapping("/github/repository-info")
    public ResponseEntity<GitHubResponse> getRepositoryInfo(@RequestParam String owner, @RequestParam String repo) {
        GitHubResponse response = new GitHubResponse();
        return ResponseEntity.ok(response);
    }
}
