package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.api.ApiErrorResponse;
import edu.java.bot.dto.scrapper.response.LinkResponse;
import edu.java.bot.dto.scrapper.response.ListLinksResponse;
import edu.java.bot.exceptions.api.ApiBadRequestException;
import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {
    @Mock
    private ScrapperClient scrapperClient;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private ListCommand listCommand;

    @Test
    @DisplayName("Успешная обработка команды /list с наличием ссылок")
    void testHandle_Success() {
        long chatId = 12345L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);

        ListLinksResponse linksResponse = new ListLinksResponse(List.of(
            new LinkResponse(1L, URI.create("https://github.com/khasanov-rail/java-course-backend"))), 1
        );
        when(scrapperClient.getLinks(chatId)).thenReturn(ResponseEntity.ok(linksResponse));

        String result = listCommand.handle(update);

        assertEquals("1. https://github.com/khasanov-rail/java-course-backend", result);
    }

    @Test
    @DisplayName("Обработка команды /list при пустом списке ссылок")
    void testHandle_EmptyList() {
        long chatId = 12345L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);

        ListLinksResponse linksResponse = new ListLinksResponse(List.of(), 0);
        when(scrapperClient.getLinks(chatId)).thenReturn(ResponseEntity.ok(linksResponse));

        String result = listCommand.handle(update);

        assertEquals("Список отслеживаемых ссылок пуст.", result);
    }

    @Test
    @DisplayName("Обработка команды /list с ошибкой API")
    void testHandle_ApiBadRequestException() {
        long chatId = 12345L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);

        when(scrapperClient.getLinks(chatId)).thenThrow(new ApiBadRequestException(Mockito.mock(ApiErrorResponse.class)));

        String result = listCommand.handle(update);

        assertEquals("Ошибка! Попробуйте позже!", result);
    }

    @Test
    @DisplayName("Проверка корректности вызова команды /list")
    void testIsCorrect_True() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/list");

        assertTrue(listCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка некорректности вызова команды /list с параметрами")
    void testIsCorrect_False() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("not_list");

        assertFalse(listCommand.isCorrect(update));
    }
}
