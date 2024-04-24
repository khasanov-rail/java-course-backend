package edu.java.scrapper.jdbcTest;

import edu.java.scrapper.integrationTest.IntegrationEnvironment;
import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repositoty.jdbc.JdbcChatsRepository;
import edu.java.scrapper.repositoty.jdbc.JdbcLinksRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcLinksRepositoryTest extends IntegrationEnvironment {

    private static JdbcLinksRepository linksRepository;
    private static JdbcChatsRepository chatsRepository;

    private static final String testUrl = "https://test.com";
    private static final Chat testChat = new Chat(1L, "Test Chat");

    @BeforeAll
    public static void setUp() {
        linksRepository = new JdbcLinksRepository(jdbcTemplate);
        chatsRepository = new JdbcChatsRepository(jdbcTemplate);

        chatsRepository.add(testChat.getId(), testChat.getName());
        linksRepository.add(testUrl);
        linksRepository.addRelationship(1L, testChat.getId());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Добавление ссылки и проверка ее существования")
    void addTest() {
        Optional<Link> link = linksRepository.findLinkByUrl(testUrl);
        Assertions.assertTrue(link.isPresent());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск ссылки по URL")
    void findLinkByUrlTest() {
        Optional<Link> link = linksRepository.findLinkByUrl(testUrl);
        Assertions.assertTrue(link.isPresent());
        Assertions.assertEquals(link.get().getUrl().toString(), testUrl);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Удаление ссылки и проверка ее отсутствия")
    void removeTest() {
        linksRepository.remove(testChat.getId(), 1L);
        Optional<Link> link = linksRepository.findLinkByUrl(testUrl);
        Assertions.assertTrue(link.isEmpty());

        linksRepository.add(testUrl);
        linksRepository.addRelationship(2L, testChat.getId());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск ссылки по ID")
    void findByIdTest() {
        Link link = linksRepository.findById(linksRepository.findLinkByUrl(testUrl).get().getId());
        Assertions.assertEquals(linksRepository.findLinkByUrl(testUrl).get().getId(), link.getId());
        Assertions.assertEquals(testUrl, link.getUrl().toString());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск ссылок для проверки")
    void findLinksToCheckTest() {
        List<Link> linkList = linksRepository.findLinksToCheck(OffsetDateTime.now().minusNanos(1));

        Assertions.assertEquals(1L, linkList.get(0).getId());
        Assertions.assertEquals(testUrl, linkList.get(0).getUrl().toString());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск всех ссылок по ID чата")
    void findAllByChatIdTest() {
        List<Link> linkList = linksRepository.findAllByChatId(testChat.getId());

        Assertions.assertEquals(linksRepository.findLinkByUrl(testUrl).get().getId(), linkList.get(0).getId());
        Assertions.assertEquals(testUrl, linkList.get(0).getUrl().toString());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Проверка уже добавленной ссылки для чата (true)")
    void isLinkAlreadyAddedForChatTrueTest() {
        boolean isLinkAlreadyAddedForChat = linksRepository.isLinkAlreadyAddedForChat(testUrl, testChat.getId());

        Assertions.assertTrue(isLinkAlreadyAddedForChat);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Проверка уже добавленной ссылки для чата (false)")
    void isLinkAlreadyAddedForChatFalseTest() {
        boolean isLinkAlreadyAddedForChat =
            linksRepository.isLinkAlreadyAddedForChat("https://test1.com", testChat.getId());
        Assertions.assertFalse(isLinkAlreadyAddedForChat);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Получение ID чата по ID ссылки")
    void tgChatIdsByLinkIdTest() {
        List<Long> chatIdList = linksRepository.tgChatIdsByLinkId(linksRepository.findLinkByUrl(testUrl).get().getId());
        Assertions.assertEquals(1L, chatIdList.get(0));
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Обновление времени последней проверки ссылки")
    void updateCheckAtTest() {
        OffsetDateTime localDateTime = OffsetDateTime.now().minusDays(1);
        Link linkAfterUpdate = new Link(1L, URI.create(testUrl), localDateTime);

        linksRepository.updateCheckAt(localDateTime, 1L);

        Link link = linksRepository.findById(1L);
        Assertions.assertEquals(link.getCheckedAt().getMinute(), linkAfterUpdate.getCheckedAt().getMinute());
    }

}
