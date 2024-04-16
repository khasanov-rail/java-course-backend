package edu.java.scrapper.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubOwner(
    @JsonProperty("login")
    String login,

    @JsonProperty("id")
    long id
) {
}
