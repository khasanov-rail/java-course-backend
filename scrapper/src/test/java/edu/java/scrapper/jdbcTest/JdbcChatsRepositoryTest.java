package edu.java.scrapper.jdbcTest;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.domain.repositoty.JdbcChatsRepository;
import edu.java.scrapper.integrationTest.IntegrationEnvironment;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcChatsRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatsRepository chatsRepository;

    private static final Chat testChat = new Chat(11L, "Test Chat");

    @Test
    @Transactional
    @Rollback
    @DisplayName("Добавление чата в базу данных")
    void addTest() {
        chatsRepository.add(testChat.getId(), testChat.getName());
        Optional<Chat> chat = chatsRepository.findChatById(testChat.getId());
        Assertions.assertEquals(chat.get(), testChat);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Удаление чата из базы данных")
    void removeTest() {
        chatsRepository.add(testChat.getId(), testChat.getName());

        chatsRepository.remove(testChat.getId());
        Optional<Chat> chat = chatsRepository.findChatById(testChat.getId());
        Assertions.assertTrue(chat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск чата по ID")
    void findChatById() {
        chatsRepository.add(testChat.getId(), testChat.getName());

        Optional<Chat> chat = chatsRepository.findChatById(testChat.getId());
        Assertions.assertTrue(chat.isPresent());
        Assertions.assertEquals(testChat.getName(), chat.get().getName());
    }
}
