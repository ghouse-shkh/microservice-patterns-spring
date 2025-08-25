package com.learn.booking.messaging.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, String> simpleKafkaTemplate;

    private static final String SIMPLE_TOPIC = "simple-topic";

    public void sendSimpleMessage(String message) {
        String key = String.valueOf(message.length());
        simpleKafkaTemplate.send(SIMPLE_TOPIC, key, message);
    }

}
