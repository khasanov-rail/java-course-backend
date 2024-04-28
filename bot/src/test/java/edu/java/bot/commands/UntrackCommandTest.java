package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.LinkHandlerChain;
import java.net.URI;

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
class UntrackCommandTest {
    @Mock
    private LinkHandlerChain linkHandlerChain;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private Chat chat;

    @InjectMocks
    private UntrackCommand untrackCommand;

    @Test
    @DisplayName("Успешное прекращение отслеживания ссылки")
    void testHandle_Success() {
        long chatId = 12345L;
        String link = "https://github.com";
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(message.text()).thenReturn("untrack " + link);

        when(linkHandlerChain.handleRequestUnsubscribe(chatId, URI.create(link))).thenReturn(
            "Untracking started for " + link);

        String result = untrackCommand.handle(update);

        assertEquals("Untracking started for https://github.com", result);
        verify(linkHandlerChain, times(1)).handleRequestUnsubscribe(chatId, URI.create(link));
    }

    @Test
    @DisplayName("Проверка корректности команды '/untrack' с валидной ссылкой")
    void testIsCorrect_True() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("untrack https://github.com");

        boolean result = untrackCommand.isCorrect(update);

        assertTrue(result);
    }

    @Test
    @DisplayName("Проверка корректности команды '/untrack' с лишними параметрами")
    void testIsCorrect_False() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("untrack https://github.com added parametr");

        boolean result = untrackCommand.isCorrect(update);

        assertFalse(result);
    }
}
