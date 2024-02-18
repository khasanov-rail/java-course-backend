package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;

public class TrackHandler implements Handler {
    private final MessageService messageService;

    public TrackHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(Update update) {
        // Здесь будет логика добавления ссылки в отслеживаемые
        messageService.sendMessage(
            update.message().chat().id(),
            "Функционал отслеживания ссылки пока не реализован. Ваш запрос на отслеживание принят."
        );
    }
}
