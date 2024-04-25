package edu.java.scrapper.integrationTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IntegrationTest extends IntegrationEnvironment { // Убедитесь, что класс наследует IntegrationEnvironment

    @BeforeAll
    static void setupContainer() {
        IntegrationEnvironment.setup(); // Вызов метода setup, который мы определили в IntegrationEnvironment
    }

    @Test
    @DisplayName("Проверка соединения с базой данных")
    public void testConnection() {
        Assertions.assertTrue(POSTGRES.isRunning(), "Контейнер с базой данных должен быть запущен.");
        assertThat(POSTGRES.getUsername()).as("Имя пользователя базы данных должно быть 'postgres'")
            .isEqualTo("postgres");
        assertThat(POSTGRES.getPassword()).as("Пароль пользователя базы данных должен быть 'postgres'")
            .isEqualTo("postgres");
        assertThat(POSTGRES.getDatabaseName()).as("Имя базы данных должно быть 'scrapper'").isEqualTo("scrapper");
    }
}
