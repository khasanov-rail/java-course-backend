package edu.java.bot.exceptions.api;

import edu.java.bot.dto.api.ApiErrorResponse;

public class ApiReAddingException extends ApiException {
    public ApiReAddingException(ApiErrorResponse apiErrorResponse) {
        super(apiErrorResponse);
    }
}
