package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.handler.Handler;
import edu.java.bot.service.MessageService;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private final MessageService messageService;
    private final Map<String, Handler> commandHandlers = new HashMap<>();

    public CommandRegistry(MessageService messageService) {
        this.messageService = messageService;
    }

    public void registerHandler(String command, Handler handler) {
        commandHandlers.put(command, handler);
    }

    public void handleUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String command = update.message().text();
            Handler handler = commandHandlers.getOrDefault(
                command,
                u -> messageService.sendMessage(u.message().chat().id(), "Извините, я не понимаю эту команду.")
            );
            handler.handle(update);
        }
    }
}
