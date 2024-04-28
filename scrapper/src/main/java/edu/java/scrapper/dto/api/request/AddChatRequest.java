package edu.java.scrapper.dto.api.request;

import jakarta.validation.constraints.NotNull;

public record AddChatRequest(
    @NotNull String userName
) {
}
