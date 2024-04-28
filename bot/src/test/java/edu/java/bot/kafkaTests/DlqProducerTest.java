package edu.java.bot.kafkaTests;

import edu.java.bot.dto.api.LinkUpdateResponse;
import edu.java.bot.service.DlqProducer;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DlqProducer.class})
public class DlqProducerTest {

    @MockBean
    private KafkaTemplate<Long, LinkUpdateResponse> kafkaTemplate;

    @Autowired
    private DlqProducer dlqProducer;

    @AfterEach
    void resetMocks() {
        org.mockito.Mockito.reset(kafkaTemplate);
    }

    @Test
    void sendToDlqTest() {
        // Arrange
        LinkUpdateResponse update = new LinkUpdateResponse();
        update.setId(1L);
        update.setUrl(URI.create("http://example-test.com"));
        update.setDescription("Test message");
        update.setTgChatIds(List.of(123456789L));

        // Act
        dlqProducer.send(update);

        // Assert
        verify(kafkaTemplate).send(eq("dlq-topic"), eq(update));
    }
}
