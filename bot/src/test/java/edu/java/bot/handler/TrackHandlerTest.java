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

class TrackHandlerTest {

    private MessageService messageService;
    private TrackHandler trackHandler;

    @BeforeEach
    void setUp() {
        // Arrange
        messageService = mock(MessageService.class);
        trackHandler = new TrackHandler(messageService);
    }

    @Test
    @DisplayName("Отправка сообщения о принятии запроса на отслеживание")
    void testSendingTrackMessage() {
        // Arrange
        Update update = mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(update.message().chat().id()).thenReturn(123L);

        // Act
        trackHandler.handle(update);

        // Assert
        verify(messageService).sendMessage(
            eq(123L),
            Mockito.contains("Функционал отслеживания ссылки пока не реализован")
        );
    }
}
