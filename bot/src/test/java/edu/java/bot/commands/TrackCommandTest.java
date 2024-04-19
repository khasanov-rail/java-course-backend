package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.LinkHandlerChain;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackCommandTest {
    @Mock
    private LinkHandlerChain linkHandlerChain;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private Chat chat;

    @InjectMocks
    private TrackCommand trackCommand;

    @Test
    @DisplayName("Успешное отслеживание ссылки")
    void testHandle_Success() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        long chatId = 12345L;
        when(chat.id()).thenReturn(chatId);
        String link = "https://github.com";
        when(message.text()).thenReturn("track " + link);

        when(linkHandlerChain.handleRequestSubscribe(chatId, URI.create(link))).thenReturn(
            "Tracking started for " + link);

        String result = trackCommand.handle(update);

        Assertions.assertEquals("Tracking started for https://github.com", result);
        verify(linkHandlerChain, times(1)).handleRequestSubscribe(chatId, URI.create(link));
    }

    @Test
    @DisplayName("Проверка корректности команды /track")
    void testIsCorrect_True() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("track https://github.com");

        boolean result = trackCommand.isCorrect(update);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка корректности команды /track с лишними параметрами")
    void testIsCorrect_False() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("track https://github.com added parametr");

        boolean result = trackCommand.isCorrect(update);

        Assertions.assertFalse(result);
    }
}
