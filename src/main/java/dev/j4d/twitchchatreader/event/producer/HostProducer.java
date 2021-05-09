package dev.j4d.twitchchatreader.event.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class HostProducer {

    private static final Logger logger = LoggerFactory.getLogger(HostProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public HostProducer(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic.host.name}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void produce(String key, String json) {
        final var future = kafkaTemplate.send(topic, key, json);
        try {
            final var result = future.get();
            final var metadata = result.getRecordMetadata();
            final var offset = metadata.offset();
            final var partition = metadata.partition();
            logger.info("Produced event with key: {} and offset: {} to partition: {}", key, offset, partition);
        } catch (InterruptedException | ExecutionException ex) {
            logger.error("Failed to produce event with key: {}", key, ex);
        }
    }
}
