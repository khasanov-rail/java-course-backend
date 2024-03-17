package edu.java.scrapper.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public class GitHubResponse {

    @JsonProperty("owner")
    private GitHubOwner owner;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("pushed_at")
    private OffsetDateTime pushedAt;

    public GitHubResponse() {
    }

    public GitHubOwner getOwner() {
        return owner;
    }

    public void setOwner(GitHubOwner owner) {
        this.owner = owner;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public OffsetDateTime getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(OffsetDateTime pushedAt) {
        this.pushedAt = pushedAt;
    }

    @Override
    public String toString() {
        return "GitHubResponse{"
            + "owner=" + owner
            + ", fullName='" + fullName + '\''
            + ", pushedAt=" + pushedAt
            + '}';
    }
}
