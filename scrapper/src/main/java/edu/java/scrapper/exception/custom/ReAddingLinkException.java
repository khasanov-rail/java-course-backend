package edu.java.scrapper.exception.custom;

import org.springframework.http.HttpStatus;

public class ReAddingLinkException extends CustomApiException {
    public ReAddingLinkException(String msg) {
        super("Ссылка уже добавлена.", HttpStatus.CONFLICT, msg);
    }
}
