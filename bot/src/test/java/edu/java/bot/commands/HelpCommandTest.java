package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private HelpCommand helpCommand;

    @BeforeEach
    void setUp() {
        lenient().when(update.message()).thenReturn(message);
        lenient().when(message.text()).thenReturn("/help");
        lenient().when(message.chat()).thenReturn(chat);
    }

    @Test
    @DisplayName("Проверка возврата команды /help")
    void testCommand() {
        assertThat(helpCommand.command()).isEqualTo("/help");
    }

    @Test
    @DisplayName("Проверка описания команды /help")
    void testDescription() {
        assertThat(helpCommand.description()).isEqualTo(" вывести окно с командами");
    }

    @Test
    @DisplayName("Проверка обработки команды /help")
    void testHandle() {
        String sendMessage = helpCommand.handle(update);

        assertThat(sendMessage).isNotNull()
            .startsWith("Список доступных команд:")
            .contains("/start - зарегистрировать пользователя")
            .contains("/help -  вывести окно с командами")
            .contains("/track - начать отслеживание ссылки")
            .contains("/untrack - прекратить отслеживание ссылки")
            .contains("/list - показать список отслеживаемых ссылок");
    }

    @Test
    @DisplayName("Проверка корректности обработки команды /help")
    void testIsCorrect() {
        assertThat(helpCommand.isCorrect(update)).isTrue();
    }
}
