package edu.java.scrapper.scheduler;

import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.dto.github.GitHubResponse;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;

class LinkUpdaterSchedulerTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private StackOverflowClient stackOverflowClient;

    @Mock
    private Logger logger;

    @InjectMocks
    private LinkUpdaterScheduler scheduler;

    @Captor
    private ArgumentCaptor<String> logCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduler = new LinkUpdaterScheduler(gitHubClient, stackOverflowClient);
    }

    @Test
    void update_callsUpdateMethods() {
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
}
