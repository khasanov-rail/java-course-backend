package edu.java.bot.exceptions.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LinkNotFoundException extends edu.java.bot.exceptions.custom.CustomApiException {
    public LinkNotFoundException(String msg) {
        super("Ссылка не найдена.", HttpStatus.NOT_FOUND, msg);
    }
}
