package edu.java.scrapper.controller;

import edu.java.scrapper.dto.stackoverflow.Item;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import edu.java.scrapper.dto.stackoverflow.StackOverflowOwner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.OffsetDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/api/stackoverflow")
public class StackOverflowController {

    @GetMapping("/questions")
    public StackOverflowDTO getQuestions() {
        StackOverflowOwner owner = new StackOverflowOwner(1L, "Пример владельца");
        Item question = new Item(
            owner,
            OffsetDateTime.now(),
            123L,
            "Пример заголовка вопроса",
            10
        );
        StackOverflowDTO response = new StackOverflowDTO(Collections.singletonList(question));

        return response;
    }
}
