package edu.java.scrapper.exception.api;


import edu.java.scrapper.dto.api.response.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {
    protected final ApiErrorResponse apiErrorResponse;
}
