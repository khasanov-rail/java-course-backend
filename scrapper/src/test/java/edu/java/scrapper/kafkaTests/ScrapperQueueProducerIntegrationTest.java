package edu.java.scrapper.kafkaTests;

import edu.java.scrapper.configuration.kafka.KafkaProducerConfiguration;
import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import edu.java.scrapper.service.kafka.ScrapperQueueProducer;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
public class ScrapperQueueProducerIntegrationTest {
    @Autowired
    private KafkaTemplate<Long, LinkUpdateResponse> kafkaTemplate;

    @Autowired
    private KafkaProducerConfiguration kafkaProducerConfiguration;

    @Test
    void sendUpdate() {
        // Arrange
        LinkUpdateResponse update = new LinkUpdateResponse(
            1L,
            URI.create("https://github.com/author/repo/"),
            "test",
            Arrays.asList(1L, 2L, 3L)
        );
        ScrapperQueueProducer producer = new ScrapperQueueProducer(kafkaTemplate);

        // Act
        producer.sendNotification(update);

        // Assert
        assertThat(receiveFromKafkaTopic(kafkaProducerConfiguration.getUpdatesTopic())).isEqualTo(update);
    }

    private Map<String, Object> getConsumerProps() {
        Map<String, Object> props =
            KafkaTestUtils.consumerProps("test-group", "false", kafkaProducerConfiguration.getBootstrapServers());

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerConfiguration.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, LinkUpdateResponse.class);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return props;
    }

    private Object receiveFromKafkaTopic(String topic) {
        try (Consumer<Long, LinkUpdateResponse> consumer = new KafkaConsumer<>(getConsumerProps())) {
            consumer.subscribe(Collections.singletonList(topic));
            ConsumerRecords<Long, LinkUpdateResponse> records = consumer.poll(Duration.ofSeconds(5));
            assertThat(records).isNotEmpty();
            ConsumerRecord<Long, LinkUpdateResponse> record = records.iterator().next();
            return record.value();
        }
    }
}
