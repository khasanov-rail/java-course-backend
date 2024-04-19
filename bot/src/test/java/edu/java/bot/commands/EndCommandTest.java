package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
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
class EndCommandTest {
    @Mock
    private ScrapperClient scrapperClient;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private EndCommand endCommand;

    @Test
    @DisplayName("Успешное завершение работы с ботом")
    void testHandle_Success() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        long chatId = 12345L;
        when(chat.id()).thenReturn(chatId);

        String result = endCommand.handle(update);

        assertEquals("Работа с ботом успешно завершена!", result);
        verify(scrapperClient, times(1)).removeChat(chatId);
    }

    @Test
    @DisplayName("Проверка корректности обработки команды /end")
    void testIsCorrect_True() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/end");

        assertTrue(endCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка корректности обработки некорректной команды")
    void testIsCorrect_False() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("not_end");

        assertFalse(endCommand.isCorrect(update));
    }
}

