package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ListHandlerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ListHandler listHandler;

    @Test
    @DisplayName("Проверка отправки сообщения о пустом списке отслеживаемых ссылок")
    void testSendingListMessage() {
        // Arrange
        Update update = Mockito.mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(update.message().chat().id()).thenReturn(123L);

        // Act
        listHandler.handle(update);

        // Assert
        verify(messageService).sendMessage(eq(123L), Mockito.contains("Список отслеживаемых ссылок пока пуст"));
    }
}
