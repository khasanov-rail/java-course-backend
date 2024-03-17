package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageServiceTest {

    private TelegramBot bot = Mockito.mock(TelegramBot.class);
    private MessageService messageService = new MessageServiceImpl(bot);

    @Test
    @DisplayName("Отправка сообщения работает корректно")
    void testSendMessage() {
        // Arrange
        Long chatId = 123L;
        String text = "Test message";
        SendResponse sendResponse = Mockito.mock(SendResponse.class);
        when(bot.execute(any(SendMessage.class))).thenReturn(sendResponse);
        when(sendResponse.isOk()).thenReturn(true);

        // Act
        messageService.sendMessage(chatId, text);

        // Assert
        verify(bot, times(1)).execute(any(SendMessage.class));
    }
}
