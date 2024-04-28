package edu.java.scrapper.client.impl;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.configuration.retry.RetryProperties;
import edu.java.scrapper.dto.api.response.ApiErrorResponse;
import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import edu.java.scrapper.exception.api.ApiBadRequestException;
import edu.java.scrapper.exception.custom.ResourceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClientImpl implements BotClient {
    private final WebClient webClient;
    private final RetryProperties retryProperties;

    public BotClientImpl(String baseUrl, RetryProperties retryProperties) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.retryProperties = retryProperties;
    }

    @Override
    public ResponseEntity<Void> sendUpdate(LinkUpdateResponse linkUpdateRequest) {
        return webClient.post()
            .uri("/updates")
            .body(BodyInserters.fromValue(linkUpdateRequest))
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException(
                    "Try later",
                    (HttpStatus) response.statusCode()
                ))
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(Void.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }
}
