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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UntrackCommandTest {

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
    private UntrackCommand untrackCommand;

    @Test
    @DisplayName("Проверка команды '/untrack'")
    void testCommand() {
        assertEquals("/untrack", untrackCommand.command());
    }

    @Test
    @DisplayName("Проверка описания команды")
    void testDescription() {
        assertEquals("прекратить отслеживание ссылки", untrackCommand.description());
    }

    @Test
    @DisplayName("Обработка ситуации с несуществующей подпиской на ссылку")
    void testHandleWithNonexistentLink() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/untrack https://github.com/sanyarnd");
        when(message.chat()).thenReturn(chat);

        URI uri = URI.create("https://github.com/sanyarnd");
        Link mockLink = mock(Link.class);
        when(mockLink.getUri()).thenReturn(uri); // Настройка, чтобы getUri() возвращал корректный URI
        when(linkHandlerChain.handleRequestUnsubscribe(uri)).thenReturn(mockLink);
        when(repository.containsLink(anyLong(), any(Link.class))).thenReturn(false);

        String expectedResponse = String.format("Ссылки %s нет в ваших подписках.", uri);
        String actualResponse = untrackCommand.handle(update);

        assertEquals(expectedResponse, actualResponse);
        verify(repository, never()).removeLink(anyLong(), any(Link.class));
    }

    @Test
    @DisplayName("Проверка корректности обработки команды с правильным текстом")
    void testIsCorrectWithValidText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/untrack https://example.com");

        assertTrue(untrackCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка корректности обработки команды с неправильным текстом")
    void testIsCorrect_WithInvalidText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/untrack");

        assertFalse(untrackCommand.isCorrect(update));
    }
}
