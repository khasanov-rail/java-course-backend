package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ListHandlerTest {

    private MessageService messageService;
    private ListHandler listHandler;

    @BeforeEach
    void setUp() {
        // Arrange
        messageService = mock(MessageService.class);
        listHandler = new ListHandler(messageService);
    }

    @Test
    @DisplayName("Проверка отправки сообщения о пустом списке отслеживаемых ссылок")
    void testSendingListMessage() {
        // Arrange
        Update update = mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(update.message().chat().id()).thenReturn(123L);

        // Act
        listHandler.handle(update);

        // Assert
        verify(messageService).sendMessage(eq(123L), Mockito.contains("Список отслеживаемых ссылок пока пуст"));
    }
}
