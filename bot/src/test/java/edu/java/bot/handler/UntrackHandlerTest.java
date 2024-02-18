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

class UntrackHandlerTest {

    private MessageService messageService;
    private UntrackHandler untrackHandler;

    @BeforeEach
    void setUp() {
        // Arrange
        messageService = mock(MessageService.class);
        untrackHandler = new UntrackHandler(messageService);
    }

    @Test
    @DisplayName("Отправка сообщения о принятии запроса на прекращение отслеживания")
    void testSendingUntrackMessage() {
        // Arrange
        Update update = mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(update.message().chat().id()).thenReturn(123L);

        // Act
        untrackHandler.handle(update);

        // Assert
        verify(messageService).sendMessage(
            eq(123L),
            Mockito.contains("Функционал прекращения отслеживания ссылки пока не реализован")
        );
    }
}
