package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;

public class ListHandler implements Handler {
    private final MessageService messageService;

    public ListHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(Update update) {
        // Здесь будет логика вывода списка отслеживаемых ссылок
        messageService.sendMessage(
            update.message().chat().id(),
            "Список отслеживаемых ссылок пока пуст. Используйте команду /track для добавления ссылок."
        );
    }
}
