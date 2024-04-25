package edu.java.scrapper.jdbcTest;

import edu.java.scrapper.model.Chat;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repositoty.jdbc.JdbcChatsRepository;
import edu.java.scrapper.repositoty.jdbc.JdbcLinksRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcLinksRepositoryTest {

    @Autowired
    private JdbcLinksRepository linksRepository;

    @Autowired
    private JdbcChatsRepository chatsRepository;

    private static final String TEST_URL = "https://test.com";
    private static final Chat TEST_CHAT = new Chat(1L, "Test Chat");

    @BeforeEach
    public void setUp() {
        linksRepository.deleteAllLinkChats();
        linksRepository.deleteAllLinks();
        chatsRepository.deleteAll();

        chatsRepository.add(TEST_CHAT.getId(), TEST_CHAT.getName());
        linksRepository.add(TEST_URL);
        long linkId = linksRepository.findLinkByUrl(TEST_URL).orElseThrow(IllegalArgumentException::new).getId();
        linksRepository.addRelationship(linkId, TEST_CHAT.getId());

        OffsetDateTime lastCheckedDate = OffsetDateTime.now().minusDays(2);
        linksRepository.updateCheckAt(lastCheckedDate, linkId);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Добавление ссылки и проверка ее существования")
    void addTest() {
        Assertions.assertThat(linksRepository.findLinkByUrl(TEST_URL)).isPresent();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск ссылки по URL")
    void findLinkByUrlTest() throws URISyntaxException {
        Assertions.assertThat(linksRepository.findLinkByUrl(TEST_URL))
            .isPresent()
            .hasValueSatisfying(link -> {
                try {
                    Assertions.assertThat(link.getUrl()).isEqualTo(new URI(TEST_URL));
                } catch (URISyntaxException e) {
                    Assertions.fail("URL syntax was incorrect", e);
                }
            });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Удаление ссылки и проверка ее отсутствия")
    void removeTest() {
        Link link = linksRepository.findLinkByUrl(TEST_URL).orElseThrow(IllegalArgumentException::new);
        linksRepository.remove(TEST_CHAT.getId(), link.getId());
        Assertions.assertThat(linksRepository.findLinkByUrl(TEST_URL)).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск ссылки по ID")
    void findByIdTest() {
        Link link = linksRepository.findLinkByUrl(TEST_URL).orElseThrow(IllegalArgumentException::new);
        Link foundLink = linksRepository.findById(link.getId());
        Assertions.assertThat(foundLink).usingRecursiveComparison().isEqualTo(link);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск ссылок для проверки")
    void findLinksToCheckTest() {
        OffsetDateTime checkDate = OffsetDateTime.now().minusDays(1);
        List<Link> links = linksRepository.findLinksToCheck(checkDate);
        Assertions.assertThat(links).isNotEmpty();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Поиск всех ссылок по ID чата")
    void findAllByChatIdTest() throws URISyntaxException {
        List<Link> links = linksRepository.findAllByChatId(TEST_CHAT.getId());
        Assertions.assertThat(links).isNotEmpty()
            .extracting(Link::getUrl)
            .contains(new URI(TEST_URL));
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Проверка уже добавленной ссылки для чата (true)")
    void isLinkAlreadyAddedForChatTrueTest() {
        Assertions.assertThat(linksRepository.isLinkAlreadyAddedForChat(TEST_URL, TEST_CHAT.getId())).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Проверка уже добавленной ссылки для чата (false)")
    void isLinkAlreadyAddedForChatFalseTest() {
        Assertions.assertThat(linksRepository.isLinkAlreadyAddedForChat("https://notexist.com", TEST_CHAT.getId()))
            .isFalse();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Получение ID чата по ID ссылки")
    void tgChatIdsByLinkIdTest() {
        List<Long> chatIdList = linksRepository.tgChatIdsByLinkId(linksRepository.findLinkByUrl(TEST_URL)
            .orElseThrow(IllegalArgumentException::new).getId());
        Assertions.assertThat(chatIdList).contains(TEST_CHAT.getId());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Обновление времени последней проверки ссылки")
    void updateCheckAtTest() {
        Link link = linksRepository.findLinkByUrl(TEST_URL).orElseThrow(IllegalArgumentException::new);
        OffsetDateTime beforeUpdate = OffsetDateTime.now();
        linksRepository.updateCheckAt(beforeUpdate, link.getId());
        Link updatedLink = linksRepository.findById(link.getId());
        Assertions.assertThat(updatedLink.getCheckedAt()).isAfter(beforeUpdate.minusSeconds(1));
    }
}
