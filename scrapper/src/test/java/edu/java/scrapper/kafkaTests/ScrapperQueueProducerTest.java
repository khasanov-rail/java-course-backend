package edu.java.scrapper.kafkaTests;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import edu.java.scrapper.service.kafka.ScrapperQueueProducer;
import java.net.URI;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScrapperQueueProducerTest {

    @Mock
    private KafkaTemplate<Long, LinkUpdateResponse> kafkaTemplate;

    @InjectMocks
    private ScrapperQueueProducer scrapperQueueProducer;

    @Test
    void testSendNotification() {
        // Arrange
        LinkUpdateResponse update = new LinkUpdateResponse(
            1L,
            URI.create("https://github.com/author/repo/"),
            "test",
            Arrays.asList(1L, 2L, 3L)
        );

        // Act
        scrapperQueueProducer.sendNotification(update);

        // Assert
        verify(kafkaTemplate).send(any(), any());
    }
}
