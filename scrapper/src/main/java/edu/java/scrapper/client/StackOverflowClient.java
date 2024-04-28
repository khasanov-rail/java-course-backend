package edu.java.scrapper.client;

import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;

public interface StackOverflowClient {
    StackOverflowDTO fetchAnswersByQuestionId(Long id);

    String getMessage(StackOverflowDTO.Item event);
}
