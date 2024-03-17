package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.customExceptions.ChatNotFoundException;

public class MessageServiceImpl implements MessageService {
    private final TelegramBot bot;

    public MessageServiceImpl(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        // Заглушка для проверки существования чата
        if (!checkChatExists(chatId)) {
            throw new ChatNotFoundException("Чат с ID " + chatId + " не найден.");
        }
        bot.execute(new SendMessage(chatId, text));
    }

    private boolean checkChatExists(Long chatId) {
        // Временно возвращаем true для всех чатов для тестирования
        return true;
    }
}
