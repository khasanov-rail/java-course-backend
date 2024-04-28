package edu.java.scrapper.kafkaTests;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import edu.java.scrapper.service.kafka.ScrapperQueueProducer;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ScrapperQueueProducer.class})
public class ScrapperQueueProducerTest {

    @MockBean
    private KafkaTemplate<Long, LinkUpdateResponse> kafkaTemplate;

    @Autowired
    private ScrapperQueueProducer scrapperQueueProducer;

    @AfterEach
    void resetMocks() {
        org.mockito.Mockito.reset(kafkaTemplate);
    }

    @Test
    public void sendNotificationTest() {
        // Arrange
        LinkUpdateResponse update = new LinkUpdateResponse(
            1L,
            URI.create("http://example-test.com"),
            "Test message",
            List.of(123456789L)
        );

        // Act
        scrapperQueueProducer.sendNotification(update);

        // Assert
        verify(kafkaTemplate).send(any(String.class), any(LinkUpdateResponse.class));
    }
}
