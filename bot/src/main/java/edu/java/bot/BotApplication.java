package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.commands.BotCommandsSetup;
import edu.java.bot.commands.CommandRegistry;
import edu.java.bot.config.BotConfig;
import edu.java.bot.handler.HelpHandler;
import edu.java.bot.handler.ListHandler;
import edu.java.bot.handler.StartHandler;
import edu.java.bot.handler.TrackHandler;
import edu.java.bot.handler.UntrackHandler;
import edu.java.bot.service.MessageService;
import edu.java.bot.service.MessageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(BotConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BotApplication.class, args);
        BotConfig botConfig = context.getBean(BotConfig.class);

        TelegramBot bot = new TelegramBot(botConfig.getTelegramToken());
        MessageService messageService = new MessageServiceImpl(bot);
        CommandRegistry commandRegistry = new CommandRegistry(messageService);

        // Регистрация обработчиков
        commandRegistry.registerHandler("/start", new StartHandler(messageService));
        commandRegistry.registerHandler("/help", new HelpHandler(messageService));
        commandRegistry.registerHandler("/track", new TrackHandler(messageService));
        commandRegistry.registerHandler("/untrack", new UntrackHandler(messageService));
        commandRegistry.registerHandler("/list", new ListHandler(messageService));

        BotCommandsSetup.setupCommands(bot);

        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> commandRegistry.handleUpdate(update));
            return com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
