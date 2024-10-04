package com.ingress2.userms.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {
    public static final String TOPIC_USER_REG_EVENTS = "email-notifications";
    @Value(value = "${kafka.bootstrapServers:localhost:9092}")
    private String bootstrapServers;

    @Bean
    public NewTopic topicExample() {
        return TopicBuilder.name(TOPIC_USER_REG_EVENTS)
                .partitions(3) // db partitions
                .replicas(1) //
                .build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        // Depending on you Kafka Cluster setup you need to configure
        // additional properties!
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

}
