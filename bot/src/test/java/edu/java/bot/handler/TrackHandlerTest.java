package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.dto.response.GitHubInfoResponse;
import edu.java.bot.dto.response.StackOverflowInfoResponse;
import edu.java.bot.service.MessageService;
import edu.java.bot.service.ScrapperClient;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackHandlerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private ScrapperClient scrapperClient;

    @InjectMocks
    private TrackHandler trackHandler;

    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Отправка информации GitHub при запросе на отслеживание GitHub репозитория")
    void testSendingGitHubTrackMessage() {
        // Arrange
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().chat().id()).thenReturn(123L);
        when(update.message().text()).thenReturn("/track github owner/repo");

        GitHubInfoResponse gitHubInfoResponse = new GitHubInfoResponse();
        gitHubInfoResponse.setFullName("owner/repo");
        gitHubInfoResponse.setDescription("Test description");

        when(scrapperClient.fetchGitHubInfo(any())).thenReturn(Mono.just(gitHubInfoResponse));

        // Act
        trackHandler.handle(update);

        // Assert
        verify(messageService).sendMessage(eq(123L), messageCaptor.capture());
        String sentMessage = messageCaptor.getValue();
        assertTrue(sentMessage.contains("owner/repo"));
        assertTrue(sentMessage.contains("Test description"));
    }

    @Test
    @DisplayName("Отправка информации StackOverflow при запросе на отслеживание вопросов по тегу")
    void testSendingStackOverflowTrackMessage() {
        // Настройка
        Update update = mock(Update.class, RETURNS_DEEP_STUBS);
        when(update.message().chat().id()).thenReturn(123L);
        when(update.message().text()).thenReturn("/track stackoverflow java");

        StackOverflowInfoResponse stackOverflowInfoResponse = new StackOverflowInfoResponse();
        StackOverflowInfoResponse.Question question = new StackOverflowInfoResponse.Question();
        question.setTitle("Java lib or app to convert CSV to XML file? [closed]");
        question.setLink("http://stackoverflow.com/questions/123");
        question.setScore(121);
        stackOverflowInfoResponse.setQuestions(List.of(question));

        when(scrapperClient.fetchStackOverflowInfo(any())).thenReturn(Mono.just(stackOverflowInfoResponse));

        // Действие
        trackHandler.handle(update);

        // Проверка
        verify(messageService).sendMessage(eq(123L), messageCaptor.capture());
        String sentMessage = messageCaptor.getValue();
        assertTrue(sentMessage.contains("Java lib or app to convert CSV to XML file? [closed]"));
        assertTrue(sentMessage.contains("http://stackoverflow.com/questions/123"));
        assertTrue(sentMessage.contains("121"));
    }
}
