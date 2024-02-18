package edu.java.bot.commands;

import edu.java.bot.service.MessageService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandRegistryTestUncommand {

    private MessageService messageService;
    private CommandRegistry commandRegistry;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        commandRegistry = new CommandRegistry(messageService);
    }

    @Test
    @DisplayName("Отправляет сообщение при получении неизвестной команды")
    void handleUnknownCommandSendsMessage() {
        // Arrange
        Update update = mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/unknownCommand");
        when(update.message().chat()).thenReturn(mock(Chat.class));
        when(update.message().chat().id()).thenReturn(123L);

        // Act
        commandRegistry.handleUpdate(update);

        // Assert
        verify(messageService).sendMessage(eq(123L), eq("Извините, я не понимаю эту команду."));
    }
}
