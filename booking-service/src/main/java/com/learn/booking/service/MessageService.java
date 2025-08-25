package com.learn.booking.service;

import org.springframework.stereotype.Service;

import com.learn.booking.messaging.producers.MessageProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageProducer messageProducer;

    public void sendMessage(String message) {
        messageProducer.sendSimpleMessage(message);
    }

}
