package edu.java.scrapper.clients;

import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowClientImpl implements StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<StackOverflowDTO> fetchQuestions(String order, String sort, String site) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/2.3/questions")
                .queryParam("order", order)
                .queryParam("sort", sort)
                .queryParam("site", site)
                .build())
            .retrieve()
            .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse -> Mono.error(new RuntimeException("Error response from StackOverflow API")))
            .bodyToMono(StackOverflowDTO.class);
    }
}
