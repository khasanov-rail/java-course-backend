package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;

public class HelpHandler implements Handler {
    private final MessageService messageService;

    public HelpHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(Update update) {
        String helpMessage = "Вот доступные команды:\n"
            + "/start - Начать работу\n"
            + "/help - Помощь\n"
            + "/track - Отслеживать ссылку\n"
            + "/untrack - Прекратить отслеживание\n"
            + "/list - Список отслеживаемых ссылок";
        messageService.sendMessage(update.message().chat().id(), helpMessage);
    }
}
