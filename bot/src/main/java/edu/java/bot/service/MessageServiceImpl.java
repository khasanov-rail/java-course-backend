package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class MessageServiceImpl implements MessageService {
    private final TelegramBot bot;

    public MessageServiceImpl(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        bot.execute(new SendMessage(chatId, text));
    }
}
