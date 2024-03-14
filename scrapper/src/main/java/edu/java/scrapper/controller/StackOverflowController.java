package edu.java.scrapper.controller;

import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import edu.java.scrapper.exception.customExeptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/stackoverflow")
public class StackOverflowController {

    private final StackOverflowClient stackOverflowClient;

    public StackOverflowController(StackOverflowClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    @GetMapping("/questions")
    public Mono<StackOverflowDTO> getQuestions(
        @RequestParam String order,
        @RequestParam String sort,
        @RequestParam String site
    ) {
        return stackOverflowClient.fetchQuestions(order, sort, site)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(
                "Questions not found for order: " + order + ", sort: " + sort + ", site: " + site)));
    }
}
