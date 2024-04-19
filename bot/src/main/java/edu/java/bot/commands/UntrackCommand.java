package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.LinkHandlerChain;
import edu.java.bot.utils.CommandUtils;
import java.net.URI;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UntrackCommand implements Command {
    private final LinkHandlerChain linkHandlerChain;
    private static final CommandInfo COMMAND_INFO = CommandInfo.UNTRACK;

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
        URI uri = URI.create(CommandUtils.getLink(update.message().text()));
        return linkHandlerChain.handleRequestUnsubscribe(update.message().chat().id(), uri);
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().split(" ").length == 2;
    }

}
