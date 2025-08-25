package com.learn.notification.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageConsumer {

    @KafkaListener(topics = "simple-topic", groupId = "msg-group", containerFactory = "stringKafkaListenerContainerFactory")
    public void listenMessage(String message) {
        log.info("Received message : {}", message);
    }

    @KafkaListener(topics = "simple-topic", groupId = "msg-group", containerFactory = "stringKafkaListenerContainerFactory")
    public void listenMessageWithHeaders(@Payload String message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("Received message from topic: {}, partition: {}, offset: {}", topic, partition, offset);
        log.info("Message content : {}", message);
    }

}