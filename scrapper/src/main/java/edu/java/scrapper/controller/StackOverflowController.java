package edu.java.scrapper.controller;

import edu.java.scrapper.dto.stackoverflow.Item;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import edu.java.scrapper.dto.stackoverflow.StackOverflowOwner;
import java.time.OffsetDateTime;
import java.util.Collections;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/stackoverflow")
public class StackOverflowController {

    private static final Long QUESTION_ID_EXAMPLE = 123L;
    private static final long ANSWER_COUNT_EXAMPLE = 10;

    @GetMapping("/questions")
    public StackOverflowDTO getQuestions() {
        StackOverflowOwner owner = new StackOverflowOwner(1L, "Пример владельца");
        Item question = new Item(
            owner,
            OffsetDateTime.now(),
            QUESTION_ID_EXAMPLE,
            "Пример заголовка вопроса",
            ANSWER_COUNT_EXAMPLE
        );
        StackOverflowDTO response = new StackOverflowDTO(Collections.singletonList(question));

        return response;
    }
}
