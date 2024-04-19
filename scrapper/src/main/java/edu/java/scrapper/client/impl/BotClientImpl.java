package edu.java.scrapper.client.impl;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.dto.api.response.ApiErrorResponse;
import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import edu.java.scrapper.exception.api.ApiBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClientImpl implements BotClient {
    private final WebClient webClient;

    public BotClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public ResponseEntity<Void> sendUpdate(LinkUpdateResponse linkUpdateRequest) {
        return webClient.post()
            .uri("/updates")
            .body(BodyInserters.fromValue(linkUpdateRequest))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(Void.class)
            .block();
    }
}
