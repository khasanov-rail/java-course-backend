package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.CommandUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMessageProcessor {

    private List<Command> commands;
    private final ChatRepository repository;

    public SendMessage handleUpdate(Update update) {
        Long chatId = update.message().chat().id();
        String commandFromChat = CommandUtils.getCommand(update.message().text());

        for (Command command : commands) {
            if (command.command().equals(commandFromChat)) {
                return processCommand(chatId, command, update);
            }
        }
        return new SendMessage(chatId, "Неизвестная команда.");
    }

    private SendMessage processCommand(Long chatId, Command command, Update update) {
        if (!command.isCorrect(update)) {
            return new SendMessage(chatId, "Команда введена некорректно.");
        }

        if (!command.isAccessibleFor(update, repository)) {
            String responseText = """
                Эта команда доступна только для зарегистрированных пользователей.
                Пожалуйста, зарегистрируйтесь для доступа к ней.
                """;
            return new SendMessage(chatId, responseText);
        }

        return new SendMessage(chatId, command.handle(update));
    }
}
