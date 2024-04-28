package edu.java.scrapper.kafkaTests;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("testKafka")
@EmbeddedKafka(partitions = 1, topics = {"updates"})
public class KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<Long, LinkUpdateResponse> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Test
    public void testSendMessage() throws Exception {
        LinkUpdateResponse update = new LinkUpdateResponse(
            1L,
            new URI("http://example-test.com"),
            "Test message",
            List.of(123456789L)
        );

        kafkaTemplate.send("updates", update.id(), update).get(10, TimeUnit.SECONDS);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        DefaultKafkaConsumerFactory<Long, LinkUpdateResponse> consumerFactory =
            new DefaultKafkaConsumerFactory<Long, LinkUpdateResponse>(
                consumerProps,
                new LongDeserializer(),
                new JsonDeserializer<>(LinkUpdateResponse.class)
            );
        Consumer<Long, LinkUpdateResponse> consumer = consumerFactory.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "updates");
        ConsumerRecords<Long, LinkUpdateResponse> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(10));
        assertThat(records.count()).isGreaterThan(0);

        for (ConsumerRecord<Long, LinkUpdateResponse> received : records) {
            assertThat(received.value().description()).isEqualTo("Test message");
        }

        consumer.close();
    }
}
