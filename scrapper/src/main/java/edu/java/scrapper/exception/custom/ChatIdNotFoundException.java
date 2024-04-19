package edu.java.scrapper.exception.custom;


import org.springframework.http.HttpStatus;

public class ChatIdNotFoundException extends CustomApiException {
    public ChatIdNotFoundException(String msg) {
        super("Аккаунт не зарегистрирован.", HttpStatus.NOT_FOUND, msg);
    }
}
