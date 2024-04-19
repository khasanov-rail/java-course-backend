package edu.java.bot.exceptions.api;

import edu.java.bot.dto.api.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {
    protected final ApiErrorResponse apiErrorResponse;
}
