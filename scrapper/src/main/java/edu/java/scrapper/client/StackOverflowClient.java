package edu.java.scrapper.client;

import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;

public interface StackOverflowClient {
    StackOverflowDTO fetchQuestion(Long id);
}
