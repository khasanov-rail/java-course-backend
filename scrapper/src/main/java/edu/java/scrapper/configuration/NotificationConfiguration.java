package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.service.kafka.ScrapperQueueProducer;
import edu.java.scrapper.service.scheduler.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {
    @Value("${spring.kafka.use-queue}")
    private boolean useQueue;

    @Bean
    public NotificationService notificationService(ScrapperQueueProducer scrapperQueueProducer, BotClient botClient) {
        return useQueue ? scrapperQueueProducer : (NotificationService) botClient;
    }
}
