package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class HelpHandlerTest {

    private MessageService messageService;
    private HelpHandler helpHandler;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        helpHandler = new HelpHandler(messageService);
    }

    @Test
    @DisplayName("Отправка сообщения с описанием команд")
    void testSendingHelpMessage() {
        // Arrange
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);

        // Act
        helpHandler.handle(update);

        // Assert
        verify(messageService).sendMessage(eq(123L), anyString());
    }
}
