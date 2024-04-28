package edu.java.scrapper.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowOwner(
    @JsonProperty("display_name")
    String displayName
) {
}
