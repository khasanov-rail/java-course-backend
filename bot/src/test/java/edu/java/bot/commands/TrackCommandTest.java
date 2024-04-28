package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.Link;
import edu.java.bot.link.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackCommandTest {

    @Mock
    private ChatRepository repository;

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
    @DisplayName("Проверка команды /track")
    void testCommand() {
        assertEquals("/track", trackCommand.command());
    }

    @Test
    @DisplayName("Проверка описания команды")
    void testDescription() {
        assertEquals("начать отслеживание ссылки", trackCommand.description());
    }

    @Test
    @DisplayName("Обработка поддерживаемой ссылки")
    void testHandle_WithSupportedLink() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/track https://github.com/sanyarnd/tinkoff-java-course-2023/");

        URI uri = URI.create("https://github.com/sanyarnd/tinkoff-java-course-2023/");
        Link mockLink = mock(Link.class);
        when(linkHandlerChain.handleRequestSubscribe(uri)).thenReturn(mockLink);

        String expectedResponse = "Ссылка https://github.com/sanyarnd/tinkoff-java-course-2023/ успешно добавлена.";
        String actualResponse = trackCommand.handle(update);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Обработка не поддерживаемой ссылки")
    void testHandle_WithUnsupportedLink() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/track https://github.com/sanyarnd/tinkoff-java-course-2023/");

        URI uri = URI.create("https://github.com/sanyarnd/tinkoff-java-course-2023/");
        when(linkHandlerChain.handleRequestSubscribe(uri)).thenReturn(null);

        String expectedResponse =
            "Извините, ссылка https://github.com/sanyarnd/tinkoff-java-course-2023/ не поддерживается.";
        String actualResponse = trackCommand.handle(update);

        assertEquals(expectedResponse, actualResponse);
        verify(repository, never()).addLink(anyLong(), any(Link.class));
    }

    @Test
    @DisplayName("Проверка корректности команды с валидным текстом")
    void testIsCorrect_WithValidText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track https://github.com/sanyarnd/tinkoff-java-course-2023/");

        assertTrue(trackCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка корректности команды с невалидным текстом")
    void testIsCorrect_WithInvalidText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track");

        assertFalse(trackCommand.isCorrect(update));
    }
}
