package edu.java.bot.commands;

import edu.java.bot.handler.*;
import edu.java.bot.service.MessageService;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandRegistryTest {

    private MessageService messageService;
    private CommandRegistry commandRegistry;
    private Handler startHandler, helpHandler, trackHandler, untrackHandler, listHandler;

    @BeforeEach
    void setUp() {
        // Arrange
        messageService = mock(MessageService.class);
        commandRegistry = new CommandRegistry(messageService);

        // Создаем моки для каждого обработчика
        startHandler = mock(StartHandler.class);
        helpHandler = mock(HelpHandler.class);
        trackHandler = mock(TrackHandler.class);
        untrackHandler = mock(UntrackHandler.class);
        listHandler = mock(ListHandler.class);

        // Регистрируем обработчики в CommandRegistry
        commandRegistry.registerHandler("/start", startHandler);
        commandRegistry.registerHandler("/help", helpHandler);
        commandRegistry.registerHandler("/track", trackHandler);
        commandRegistry.registerHandler("/untrack", untrackHandler);
        commandRegistry.registerHandler("/list", listHandler);
    }

    @Test
    @DisplayName("Отправляет сообщение при получении неизвестной команды")
    void handleUnknownCommandSendsMessage() {
        // Arrange
        Update update = mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn("/unknownCommand");
        when(update.message().chat().id()).thenReturn(123L);

        // Act
        commandRegistry.handleUpdate(update);

        // Assert
        verify(messageService).sendMessage(eq(123L), eq("Извините, я не понимаю эту команду."));
    }

    private void testCommandHandling(String command, Handler expectedHandler) {
        // Arrange
        Update update = mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
        when(update.message().text()).thenReturn(command);

        // Act
        commandRegistry.handleUpdate(update);

        // Assert
        verify(expectedHandler).handle(update);
    }

    @Test
    @DisplayName("Обработка команды /start вызывает StartHandler")
    void whenStartCommand_thenStartHandlerInvoked() {
        testCommandHandling("/start", startHandler);
    }

    @Test
    @DisplayName("Обработка команды /help вызывает HelpHandler")
    void whenHelpCommand_thenHelpHandlerInvoked() {
        testCommandHandling("/help", helpHandler);
    }

    @Test
    @DisplayName("Обработка команды /track вызывает TrackHandler")
    void whenTrackCommand_thenTrackHandlerInvoked() {
        testCommandHandling("/track", trackHandler);
    }

    @Test
    @DisplayName("Обработка команды /untrack вызывает UntrackHandler")
    void whenUntrackCommand_thenUntrackHandlerInvoked() {
        testCommandHandling("/untrack", untrackHandler);
    }

    @Test
    @DisplayName("Обработка команды /list вызывает ListHandler")
    void whenListCommand_thenListHandlerInvoked() {
        testCommandHandling("/list", listHandler);
    }
}
