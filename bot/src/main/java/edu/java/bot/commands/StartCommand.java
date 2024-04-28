package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.ChatRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartCommand implements Command {
    private final ChatRepository repository;
    private static final edu.java.bot.commands.CommandInfo COMMAND_INFO = edu.java.bot.commands.CommandInfo.START;

    @Override
    public String command() {
        return COMMAND_INFO.getCommand();
    }

    @Override
    public String description() {
        return COMMAND_INFO.getDescription();
    }

    @Override
    public String handle(Update update) {
        long id = update.message().chat().id();
        String message;

        if (repository.isRegistered(id)) {
            message = "Извините, но данный аккаунт уже зарегистрирован.";
        } else {
            repository.register(id);
            message = "Регистрация прошла успешно. Добро пожаловать!";
        }

        return message;
    }

    @Override
    public boolean isCorrect(Update update) {
        return command().equals(update.message().text());
    }
}
