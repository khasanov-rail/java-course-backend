package edu.java.scrapper.configuration.repository;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.repositoty.jdbc.JdbcChatsRepository;
import edu.java.scrapper.repositoty.jdbc.JdbcLinksRepository;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import edu.java.scrapper.service.jdbc.JdbcLinkUpdater;
import edu.java.scrapper.service.jdbc.JdbcTgChatService;
import edu.java.scrapper.service.scheduler.NotificationService;
import io.micrometer.core.instrument.Counter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcLinkService jdbcLinkService(
        JdbcLinksRepository jdbcLinksRepository,
        JdbcChatsRepository jdbcChatsRepository
    ) {
        return new JdbcLinkService(jdbcChatsRepository, jdbcLinksRepository);
    }

    @Bean
    public JdbcTgChatService jdbcTgChatService(JdbcChatsRepository jdbcChatsRepository) {
        return new JdbcTgChatService(jdbcChatsRepository);
    }

    @Bean
    public JdbcLinkUpdater jdbcUpdater(
        JdbcLinksRepository jdbcLinksRepository,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        NotificationService notificationService,
        Counter processedUpdatesCounter
    ) {
        return new JdbcLinkUpdater(
            jdbcLinksRepository,
            gitHubClient,
            stackOverflowClient,
            notificationService,
            processedUpdatesCounter
        );
    }
}
