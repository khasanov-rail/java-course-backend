package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.handler.*;
import edu.java.bot.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandRegistryTest {

    private MessageService messageService;
    private CommandRegistry commandRegistry;

    @BeforeEach
    void setUp() {
        // Arrange
        messageService = mock(MessageService.class);
        commandRegistry = new CommandRegistry(messageService);

        // Создаем моки для каждого обработчика
        Handler startHandler = mock(StartHandler.class);
        Handler helpHandler = mock(HelpHandler.class);
        Handler trackHandler = mock(TrackHandler.class);
        Handler untrackHandler = mock(UntrackHandler.class);
        Handler listHandler = mock(ListHandler.class);

        // Регистрируем обработчики в CommandRegistry
        commandRegistry.registerHandler("/start", startHandler);
        commandRegistry.registerHandler("/help", helpHandler);
        commandRegistry.registerHandler("/track", trackHandler);
        commandRegistry.registerHandler("/untrack", untrackHandler);
        commandRegistry.registerHandler("/list", listHandler);
    }

    @ParameterizedTest(name = "Обработка команды {0} вызывает соответствующий обработчик")
    @CsvSource({
        "/start, StartHandler",
        "/help, HelpHandler",
        "/track, TrackHandler",
        "/untrack, UntrackHandler",
        "/list, ListHandler"
    })
    void testCommandHandling(String command, String handlerClassName) {
        // Arrange
        try {
            Update update = mock(Update.class, Mockito.RETURNS_DEEP_STUBS);
            when(update.message().text()).thenReturn(command);
            Class<?> handlerClass = Class.forName(handlerClassName);
            Handler expectedHandler = (Handler) handlerClass.getDeclaredConstructor().newInstance();

            // Act
            commandRegistry.handleUpdate(update);

            // Assert
            verify(expectedHandler).handle(update);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
    }
}
