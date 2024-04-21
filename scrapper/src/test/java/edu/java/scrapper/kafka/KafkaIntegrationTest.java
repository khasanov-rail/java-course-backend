package edu.java.scrapper.kafka;

import edu.java.scrapper.dto.bot.LinkUpdateResponse;
import java.net.URI;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
    "spring.kafka.bootstrap-servers=localhost:29091"
})
@Testcontainers
public class KafkaIntegrationTest {

    @Container
    public static KafkaContainer kafkaContainer =
        new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.1"));

    @Autowired
    private KafkaTemplate<Long, LinkUpdateResponse> kafkaTemplate;

    @Test
    public void testSendingKafkaMessage() throws Exception {

        Map<String, Object> consumerProps =
            KafkaTestUtils.consumerProps("testGroup", "true", kafkaContainer.getBootstrapServers());
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        Consumer<Long, LinkUpdateResponse> consumer = new DefaultKafkaConsumerFactory<>(
            consumerProps,
            new JsonDeserializer<>(Long.class),
            new JsonDeserializer<>(LinkUpdateResponse.class)
        ).createConsumer();
        consumer.subscribe(java.util.Collections.singletonList("updates"));

        LinkUpdateResponse message =
            new LinkUpdateResponse(1L, new URI("http://example.com"), "Description", java.util.Arrays.asList(1L, 2L));
        kafkaTemplate.send("updates", message.id(), message);

        ConsumerRecord<Long, LinkUpdateResponse> received = KafkaTestUtils.getSingleRecord(consumer, "updates");
        assertEquals(message.id(), received.key());
        assertEquals(message.url(), received.value().url());

        consumer.close();
    }
}




