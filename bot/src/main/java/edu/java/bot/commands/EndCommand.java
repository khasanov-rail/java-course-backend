package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.exceptions.api.ApiBadRequestException;
import edu.java.bot.exceptions.api.ApiNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EndCommand implements Command {
    private final ScrapperClient client;
    private static final CommandInfo COMMAND_INFO = CommandInfo.END;

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
        String message;
        long id = update.message().chat().id();
        try {
            client.removeChat(id);
            message = "Работа с ботом успешно завершена!";
        } catch (ApiNotFoundException e) {
            message = e.getApiErrorResponse().description();
        } catch (ApiBadRequestException e) {
            message = "Ошибка! Попробуйте позже!";
        }
        return message;
    }

    @Override
    public boolean isCorrect(Update update) {
        return command().equals(update.message().text());
    }
}
