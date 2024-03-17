package edu.java.scrapper.controller;

import edu.java.scrapper.clients.StackOverflowClient;
import edu.java.scrapper.dto.stackoverflow.StackOverflowDTO;
import edu.java.scrapper.exception.customExeptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StackOverflowControllerTest {

    @Mock
    private StackOverflowClient stackOverflowClient;

    @InjectMocks
    private StackOverflowController stackOverflowController;

    @Test
    @DisplayName("Успешное получение списка вопросов StackOverflow")
    void getQuestionsSuccess() {
        // Arrange
        String order = "desc";
        String sort = "activity";
        String site = "stackoverflow";
        StackOverflowDTO stackOverflowDTO = new StackOverflowDTO(null);
        when(stackOverflowClient.fetchQuestions(order, sort, site)).thenReturn(Mono.just(stackOverflowDTO));

        // Act
        Mono<StackOverflowDTO> result = stackOverflowController.getQuestions(order, sort, site);

        // Assert
        result.subscribe(response -> assertThat(response).isEqualTo(stackOverflowDTO));
    }

    @Test
    @DisplayName("Обработка исключения при отсутствии вопросов")
    void getQuestionsHandlesResourceNotFoundException() {
        // Arrange
        String order = "nonexistent";
        String sort = "nonexistent";
        String site = "nonexistent";
        when(stackOverflowClient.fetchQuestions(order, sort, site)).thenReturn(Mono.error(new ResourceNotFoundException(
            "Questions not found for order: " + order + ", sort: " + sort + ", site: " + site)));

        // Act
        Mono<StackOverflowDTO> result = stackOverflowController.getQuestions(order, sort, site);

        // Assert
        result.subscribe(
            response -> {
            },
            throwable -> {
                assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
                assertThat(throwable.getMessage()).isEqualTo(
                    "Questions not found for order: " + order + ", sort: " + sort + ", site: " + site);
            }
        );
    }
}

