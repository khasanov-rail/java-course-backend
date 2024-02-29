package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;

public class UntrackHandler implements Handler {
    private final MessageService messageService;

    public UntrackHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(Update update) {
        // Здесь будет логика удаления ссылки из отслеживаемых
        messageService.sendMessage(
            update.message().chat().id(),
            "Функционал прекращения отслеживания ссылки пока не реализован. Ваш запрос на удаление отслеживания принят."
        );
    }
}
