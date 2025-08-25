package com.learn.notification.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.learn.notification.messaging.events.BookingCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingEventConsumer {

    @KafkaListener(topics = "booking-created", groupId = "booking-group", containerFactory = "bookingKafkaListenerContainerFactory")
    public void listenBookingCreatedEvent(BookingCreatedEvent event) {
        log.info("Consumr One: Booking created event: {}", event);
    }

}
