package edu.java.scrapper.integrationTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static edu.java.scrapper.integrationTest.IntegrationEnvironment.POSTGRES;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {

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
