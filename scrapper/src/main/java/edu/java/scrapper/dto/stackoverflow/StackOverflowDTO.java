package edu.java.scrapper.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowDTO(
    @JsonProperty("items") List<edu.java.scrapper.dto.stackoverflow.Item> items
) {
}
