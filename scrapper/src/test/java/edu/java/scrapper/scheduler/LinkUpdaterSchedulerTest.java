package edu.java.scrapper.scheduler;

import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.dto.github.GitHubResponse;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkUpdaterSchedulerTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private StackOverflowClient stackOverflowClient;

    @InjectMocks
    private LinkUpdaterScheduler scheduler;

    @Test
    void update_callsUpdateMethodsSuccessfully() {
        // Arrange
        GitHubResponse gitHubResponse = new GitHubResponse();
        StackOverflowDTO stackOverflowDTO = new StackOverflowDTO(null);

        when(gitHubClient.fetchRepositoryInfo(anyString(), anyString())).thenReturn(Mono.just(gitHubResponse));
        when(stackOverflowClient.fetchQuestions(anyString(), anyString(), anyString())).thenReturn(Mono.just(
            stackOverflowDTO));

        // Act
        scheduler.update();

        // Assert
        verify(gitHubClient, times(1)).fetchRepositoryInfo(anyString(), anyString());
        verify(stackOverflowClient, times(1)).fetchQuestions(anyString(), anyString(), anyString());
    }

    @Test
    void update_handlesErrorsGracefully() {
        // Arrange
        when(gitHubClient.fetchRepositoryInfo(anyString(), anyString())).thenReturn(Mono.error(new RuntimeException(
            "GitHub error")));
        when(stackOverflowClient.fetchQuestions(
            anyString(),
            anyString(),
            anyString()
        )).thenReturn(Mono.error(new RuntimeException("StackOverflow error")));

        // Act
        scheduler.update();

        // Assert
        verify(gitHubClient, times(1)).fetchRepositoryInfo(anyString(), anyString());
        verify(stackOverflowClient, times(1)).fetchQuestions(anyString(), anyString(), anyString());
    }
}
