package edu.java.scrapper.client;

import edu.java.scrapper.dto.github.GitHubDTO;

public interface GitHubClient {
    GitHubDTO fetchRepo(String owner, String repo);
}
