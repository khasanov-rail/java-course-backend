package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.scrapper.request.AddChatRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {
    @Mock
    private ScrapperClient scrapperClient;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private StartCommand startCommand;

    private long chatId = 12345L;

    @Test
    @DisplayName("Успешная регистрация пользователя")
    void testHandle_Success() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        String username = "testUser";
        when(message.chat().username()).thenReturn(username);

        String result = startCommand.handle(update);

        assertEquals("Регистрация прошла успешно. Добро пожаловать!", result);
        verify(scrapperClient, times(1)).addChat(chatId, new AddChatRequest(username));
    }

    @Test
    @DisplayName("Проверка корректности вызова команды /start")
    void testIsCorrect_True() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");

        assertTrue(startCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка некорректности вызова команды /start с ошибочным текстом")
    void testIsCorrect_False() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("not_start");

        assertFalse(startCommand.isCorrect(update));
    }
}

