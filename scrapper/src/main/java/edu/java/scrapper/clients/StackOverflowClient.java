package edu.java.scrapper.clients;

import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import reactor.core.publisher.Mono;

public interface StackOverflowClient {
    Mono<StackOverflowDTO> fetchQuestions(String order, String sort, String site);
}
