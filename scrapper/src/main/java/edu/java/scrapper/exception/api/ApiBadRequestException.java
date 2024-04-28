package edu.java.scrapper.exception.api;

import edu.java.scrapper.dto.api.response.ApiErrorResponse;
import lombok.Getter;

@Getter
public class ApiBadRequestException extends ApiException {
    public ApiBadRequestException(ApiErrorResponse apiErrorResponse) {
        super(apiErrorResponse);
    }
}
