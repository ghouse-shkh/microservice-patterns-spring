package com.learn.booking.messaging.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.learn.booking.messaging.events.BookingCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingProducer {
    private final KafkaTemplate<String, BookingCreatedEvent> bookingKafkaTemplate;

    private static final String BOOKING_TOPIC = "booking-created";

    public void sendBookingEvent(BookingCreatedEvent event) {
        String key = String.valueOf(event.getBookingId());
        bookingKafkaTemplate.send(BOOKING_TOPIC, key, event);
    }
}