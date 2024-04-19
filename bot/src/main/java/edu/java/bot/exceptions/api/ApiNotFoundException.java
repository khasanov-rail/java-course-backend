package edu.java.bot.exceptions.api;

import edu.java.bot.dto.api.ApiErrorResponse;
import lombok.Getter;

@Getter
public class ApiNotFoundException extends ApiException {
    public ApiNotFoundException(ApiErrorResponse apiErrorResponse) {
        super(apiErrorResponse);
    }
}
