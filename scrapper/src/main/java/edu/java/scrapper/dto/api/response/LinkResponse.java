package edu.java.scrapper.dto.api.response;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkResponse(
    Long id,
    @NotNull URI url
) {
}
