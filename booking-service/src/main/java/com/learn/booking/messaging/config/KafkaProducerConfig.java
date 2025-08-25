package com.learn.booking.messaging.config;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.learn.booking.messaging.events.BookingCreatedEvent;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    // If there are no custom Kafkatemplates created, then KafkaTemplate will be
    // created automatically with default configuration
    @Bean
    public ProducerFactory<String, String> stringProducerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class));
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> simpleProducerFactory) {
        return new KafkaTemplate<>(simpleProducerFactory);
    }

    @Bean
    public ProducerFactory<String, BookingCreatedEvent> bookingProducerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                JsonSerializer.ADD_TYPE_INFO_HEADERS, false));
    }

    @Bean
    public KafkaTemplate<String, BookingCreatedEvent> bookingKafkaTemplate(
            ProducerFactory<String, BookingCreatedEvent> bookingProducerFactory) {
        return new KafkaTemplate<>(bookingProducerFactory);
    }

        // Topic creation if allowed and KafkaTemplate Configuration
    @Bean
    public NewTopic bookingCreatedTopic() {
        return TopicBuilder.name("booking-created")
                .partitions(3)
                .replicas(1)
                .build();
    }
}