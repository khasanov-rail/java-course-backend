package edu.java.scrapper.controller;

import edu.java.scrapper.clients.GitHubClient;
import edu.java.scrapper.dto.github.GitHubResponse;
import edu.java.scrapper.exception.customExeptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubControllerTest {

    @Mock
    private GitHubClient gitHubClient;

    @InjectMocks
    private GitHubController gitHubController;

    @Test
    @DisplayName("Получение информации о репозитории: успешно")
    void getRepositoryInfoSuccess() {
        // Arrange
        String owner = "test-owner";
        String repo = "test-repo";
        GitHubResponse gitHubResponse = new GitHubResponse();
        when(gitHubClient.fetchRepositoryInfo(owner, repo)).thenReturn(Mono.just(gitHubResponse));

        // Act
        Mono<ResponseEntity<GitHubResponse>> result = gitHubController.getRepositoryInfo(owner, repo);

        // Assert
        result.subscribe(response -> {
            assertThat(response.getStatusCodeValue()).isEqualTo(200);
            assertThat(response.getBody()).isEqualTo(gitHubResponse);
        });
    }

    @Test
    @DisplayName("Обработка ResourceNotFoundException при получении информации о репозитории")
    void getRepositoryInfoHandlesResourceNotFoundException() {
        // Arrange
        String owner = "nonexistent-owner";
        String repo = "nonexistent-repo";
        when(gitHubClient.fetchRepositoryInfo(owner, repo)).thenReturn(Mono.error(new ResourceNotFoundException(
            "Repository not found for owner: " + owner + " and repo: " + repo)));

        // Act
        Mono<ResponseEntity<GitHubResponse>> result = gitHubController.getRepositoryInfo(owner, repo);

        // Assert
        result.subscribe(
            response -> {
            },
            throwable -> {
                assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
                assertThat(throwable.getMessage()).isEqualTo(
                    "Repository not found for owner: " + owner + " and repo: " + repo);
            }
        );
    }
}

