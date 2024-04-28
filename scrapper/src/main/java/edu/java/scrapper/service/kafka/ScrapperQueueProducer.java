package edu.java.scrapper.service.kafka;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import edu.java.scrapper.service.scheduler.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScrapperQueueProducer implements NotificationService {
    private final KafkaTemplate<Long, LinkUpdateResponse> kafkaProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapperQueueProducer.class);

    @Value("${spring.kafka.topic}")
    private String topic;

    @PostConstruct
    public void logConfig() {
        LOGGER.info("Используется тема Kafka: {}", topic);
    }

    @Override
    public void sendNotification(LinkUpdateResponse update) {
        if (topic == null) {
            LOGGER.error("Kafka topic is null");
            throw new IllegalStateException("Kafka topic has not been set");
        }
        kafkaProducer.send(topic, update);
        LOGGER.info("Message sent to Kafka: {}", update);
    }
}
