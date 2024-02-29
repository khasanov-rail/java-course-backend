package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;

public final class BotCommandsSetup {

    private BotCommandsSetup() {
    }

    public static void setupCommands(TelegramBot bot) {
        BotCommand[] commands = {
            new BotCommand("/start", "Начать работу"),
            new BotCommand("/help", "Помощь"),
            new BotCommand("/track", "Отслеживать ссылку"),
            new BotCommand("/untrack", "Прекратить отслеживание"),
            new BotCommand("/list", "Список отслеживаемых ссылок")
        };

        bot.execute(new SetMyCommands(commands));
    }
}
