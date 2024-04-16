package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.ChatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    @Mock
    private ChatRepository repository;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @InjectMocks
    private StartCommand startCommand;

    @Test
    @DisplayName("Проверка команды /start")
    void testCommand() {
        assertEquals("/start", startCommand.command());
    }

    @Test
    @DisplayName("Проверка описания команды")
    void testDescription() {
        assertEquals("зарегистрировать пользователя", startCommand.description());
    }

    @Test
    @DisplayName("Обработка регистрации при отсутствии регистрации")
    void testHandle_RegistrationSuccess() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1234L);
        when(repository.isRegistered(1234L)).thenReturn(false);

        String result = startCommand.handle(update);

        assertEquals("Регистрация прошла успешно. Добро пожаловать!", result);
        verify(repository).register(1234L);
    }

    @Test
    @DisplayName("Обработка попытки регистрации уже зарегистрированного пользователя")
    void testHandle_AlreadyRegistered() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(5678L);
        when(repository.isRegistered(5678L)).thenReturn(true);

        String result = startCommand.handle(update);

        assertEquals("Извините, но данный аккаунт уже зарегистрирован.", result);
        verify(repository, never()).register(5678L);
    }

    @Test
    @DisplayName("Проверка корректности обработки команды /start")
    void testIsCorrect() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");

        assertTrue(startCommand.isCorrect(update));
    }

    @Test
    @DisplayName("Проверка некорректности обработки команды /start с параметром")
    void testIsCorrect_WrongCommand() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start log");

        assertFalse(startCommand.isCorrect(update));
    }
}
