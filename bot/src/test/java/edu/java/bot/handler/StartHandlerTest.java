package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class StartHandlerTest {

    private MessageService messageService;
    private StartHandler startHandler;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        startHandler = new StartHandler(messageService);
    }

    @Test
    @DisplayName("Отправка приветственного сообщения")
    void testSendingWelcomeMessage() {
        // Arrange
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);

        // Act
        startHandler.handle(update);

        // Assert
        verify(messageService).sendMessage(eq(123L), contains("Добро пожаловать"));
    }
}
