package edu.java.scrapper.client.impl;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.dto.bot.LinkUpdateResponse;
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
            .toEntity(Void.class)
            .block();
    }
}
