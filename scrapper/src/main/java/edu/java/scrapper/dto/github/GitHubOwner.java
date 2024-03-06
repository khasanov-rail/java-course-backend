package edu.java.scrapper.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubOwner {
    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private long id;

    // Геттеры и сеттеры
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GitHubOwner{"
            + "login='" + login + '\''
            + ", id=" + id
            + '}';
    }
}
