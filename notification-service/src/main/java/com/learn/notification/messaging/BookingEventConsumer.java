package com.learn.notification.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.learn.notification.messaging.events.BookingCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}" )
    public void handleBookingCreated(BookingCreatedEvent event) {
        log.info("Received event: {}", event);
        // THe details of this event can be sent to the customer thru an email, stored in a database, etc
    }

}
