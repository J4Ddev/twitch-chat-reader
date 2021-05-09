package dev.j4d.twitchchatreader.event.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public NewTopic chatMessageTopic(
            @Value("${kafka.topic.chat-message.name}") String name,
            @Value("${kafka.topic.chat-message.partitions}") int partitions,
            @Value("${kafka.topic.chat-message.replicas}") short replicas) {
        return new NewTopic(name, partitions, replicas);
    }

    @Bean
    public NewTopic presenceTopic(
            @Value("${kafka.topic.presence.name}") String name,
            @Value("${kafka.topic.presence.partitions}") int partitions,
            @Value("${kafka.topic.presence.replicas}") short replicas) {
        return new NewTopic(name, partitions, replicas);
    }

    @Bean
    public NewTopic hostTopic(
            @Value("${kafka.topic.host.name}") String name,
            @Value("${kafka.topic.host.partitions}") int partitions,
            @Value("${kafka.topic.host.replicas}") short replicas) {
        return new NewTopic(name, partitions, replicas);
    }

    @Bean
    public NewTopic punishmentTopic(
            @Value("${kafka.topic.punishment.name}") String name,
            @Value("${kafka.topic.punishment.partitions}") int partitions,
            @Value("${kafka.topic.punishment.replicas}") short replicas) {
        return new NewTopic(name, partitions, replicas);
    }

    @Bean
    public NewTopic userNoticeTopic(
            @Value("${kafka.topic.user-notice.name}") String name,
            @Value("${kafka.topic.user-notice.partitions}") int partitions,
            @Value("${kafka.topic.user-notice.replicas}") short replicas) {
        return new NewTopic(name, partitions, replicas);
    }
}
