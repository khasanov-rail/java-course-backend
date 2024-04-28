package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.Link;
import edu.java.bot.link.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.CommandUtils;
import java.net.URI;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UntrackCommand implements Command {
    private final ChatRepository repository;
    private final LinkHandlerChain linkHandlerChain;
    private static final edu.java.bot.commands.CommandInfo COMMAND_INFO = edu.java.bot.commands.CommandInfo.UNTRACK;

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

        URI uri = URI.create(CommandUtils.getLink(update.message().text()));
        Link link = linkHandlerChain.handleRequestUnsubscribe(uri);

        if (!repository.containsLink(id, link)) {
            return String.format("Ссылки %s нет в ваших подписках.", link.getUri());
        } else {
            repository.removeLink(id, link);
            return String.format("Ссылка %s успешно удалена.", link.getUri());
        }
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().split(" ").length == 2;
    }

}
