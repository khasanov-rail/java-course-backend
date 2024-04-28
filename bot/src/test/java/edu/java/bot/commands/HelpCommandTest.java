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
    @DisplayName("Проверка обработки команды /help")
    void testHandle() {
        String sendMessage = helpCommand.handle(update);

        assertThat(sendMessage).isNotNull();
        assertThat(sendMessage).startsWith("Список доступных команд:");
        assertThat(sendMessage).contains("/start - зарегистрировать пользователя");
        assertThat(sendMessage).contains("/help -  вывести окно с командами");
        assertThat(sendMessage).contains("/track - начать отслеживание ссылки");
        assertThat(sendMessage).contains("/untrack - прекратить отслеживание ссылки");
        assertThat(sendMessage).contains("/list - показать список отслеживаемых ссылок");
    }

    @Test
    @DisplayName("Проверка корректности обработки команды /help")
    void testIsCorrect() {
        assertThat(helpCommand.isCorrect(update)).isTrue();
    }
}
