package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;

public class StartHandler implements Handler {
    private final MessageService messageService;

    public StartHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(Update update) {
        messageService.sendMessage(
            update.message().chat().id(),
            "Добро пожаловать! Воспользуйтесь командой /help, чтобы узнать доступные команды."
        );
    }
}
