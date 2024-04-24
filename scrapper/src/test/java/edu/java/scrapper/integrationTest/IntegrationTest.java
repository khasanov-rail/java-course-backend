package edu.java.scrapper.integrationTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    @Test
    @DisplayName("Проверка соединения с базой данных")
    public void testConnection() {
        Assertions.assertTrue(
            IntegrationEnvironment.POSTGRES.isRunning(),
            "Контейнер с базой данных должен быть запущен."
        );
        org.assertj.core.api.Assertions.assertThat(IntegrationEnvironment.POSTGRES.getUsername())
            .as("Имя пользователя базы данных должно быть 'postgres'")
            .isEqualTo("postgres");
        org.assertj.core.api.Assertions.assertThat(IntegrationEnvironment.POSTGRES.getPassword())
            .as("Пароль пользователя базы данных должен быть 'postgres'")
            .isEqualTo("postgres");
        org.assertj.core.api.Assertions.assertThat(IntegrationEnvironment.POSTGRES.getDatabaseName())
            .as("Имя базы данных должно быть 'scrapper'").isEqualTo("scrapper");
    }
}
