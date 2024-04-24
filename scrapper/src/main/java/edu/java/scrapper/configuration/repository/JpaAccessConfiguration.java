package edu.java.scrapper.configuration.repository;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.repositoty.jpa.JpaChatRepository;
import edu.java.scrapper.repositoty.jpa.JpaLinkRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.LinkUpdater;
import edu.java.scrapper.service.TgChatService;
import edu.java.scrapper.service.jpa.JpaLinkService;
import edu.java.scrapper.service.jpa.JpaLinkUpdater;
import edu.java.scrapper.service.jpa.JpaTgChatService;
import edu.java.scrapper.service.scheduler.NotificationService;
import io.micrometer.core.instrument.Counter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService jpaLinkService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        return new JpaLinkService(jpaLinkRepository, jpaChatRepository);
    }

    @Bean
    public TgChatService jpaTgChatService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        return new JpaTgChatService(jpaLinkRepository, jpaChatRepository);
    }

    @Bean
    public LinkUpdater jpaUpdate(
        JpaLinkRepository jpaLinkRepository,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        NotificationService notificationService,
        Counter processedUpdatesCounter
    ) {
        return new JpaLinkUpdater(
            jpaLinkRepository,
            gitHubClient,
            stackOverflowClient,
            notificationService,
            processedUpdatesCounter
        );
    }
}

