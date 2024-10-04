package com.ingress2.userms.kafka;


import com.ingress2.userms.entity.UserEntity;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {
    @Value(value = "${kafka.bootstrapServers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.properties.retry.backoff.ms}")
    private String retry;

    @Value("${spring.kafka.producer.properties.max.block.ms}")
    private String maxBlock;

    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private String deliveryTimeout;

    @Bean
    public ProducerFactory<String, UserEntity> genericKafkaProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, retry);
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, maxBlock);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, UserEntity> genericKafkaProducer() {
        return new KafkaTemplate<>(genericKafkaProducerFactory());
    }
}
