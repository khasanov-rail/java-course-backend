package edu.java.scrapper.client;

import edu.java.scrapper.dto.github.GitHubDTO;
import java.util.List;

public interface GitHubClient {
    List<GitHubDTO> fetchRepo(String owner, String repo);

    String getMessage(GitHubDTO event);
}

