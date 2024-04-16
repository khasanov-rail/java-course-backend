package edu.java.scrapper.dto.api.response;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record ListLinksResponse(
    @NotEmpty List<LinkResponse> links,
    Integer size
) {
}
