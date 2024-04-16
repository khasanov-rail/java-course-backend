package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.Link;
import edu.java.bot.link.LinkInfo;
import edu.java.bot.repository.ChatRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {
    @Mock
    private ChatRepository repository;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private ListCommand listCommand;

    @Test
    @DisplayName("Обработка команды /list при пустом списке ссылок")
    void testHandleWithEmptyList() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(repository.getList(1L)).thenReturn(new ArrayList<>());

        String result = listCommand.handle(update);
        assertEquals("Список отслеживаемых ссылок пуст.", result);
    }

    @Test
    @DisplayName("Обработка команды /list при наличии ссылок")
    void testHandleWithNonEmptyList() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(
            LinkInfo.GITHUB,
            URI.create("https://github.com/sanyarnd/tinkoff-java-course-2023/"),
            "github.com"
        ));
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(repository.getList(1L)).thenReturn(links);

        String result = listCommand.handle(update);

        assertEquals("1. https://github.com/sanyarnd/tinkoff-java-course-2023/", result);
    }

    @Test
    @DisplayName("Проверка корректности вызова команды /list")
    void testIsCorrect() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/list");

        assertTrue(listCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка некорректности вызова команды /list с параметрами")
    void testIsNotCorrect() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/list list");

        assertFalse(listCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка возвращаемой команды")
    void testCommand() {
        assertEquals("/list", listCommand.command());
    }

    @Test
    @DisplayName("Проверка описания команды")
    void testDescription() {
        assertEquals("показать список отслеживаемых ссылок", listCommand.description());
    }
}
