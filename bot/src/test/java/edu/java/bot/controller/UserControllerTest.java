package edu.java.bot.controller;

import edu.java.bot.dto.request.UserRegistrationRequest;
import edu.java.bot.dto.response.UserRegistrationResponse;
import edu.java.bot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование UserController")
class UserControllerTest {

    private UserService userServiceMock;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userServiceMock = mock(UserService.class);
        userController = new UserController(userServiceMock);
    }

    @Test
    @DisplayName("Регистрация пользователя возвращает успешный ответ")
    void registerUserReturnsSuccess() {
        // Arrange
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");

        UserRegistrationResponse expectedResponse = new UserRegistrationResponse("User registered successfully");
        when(userServiceMock.registerUser(any(UserRegistrationRequest.class))).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UserRegistrationResponse> response = userController.registerUser(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualToComparingFieldByField(expectedResponse);
        Mockito.verify(userServiceMock).registerUser(request);
    }
}
