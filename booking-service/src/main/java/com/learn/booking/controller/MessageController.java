package com.learn.booking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.booking.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messaging")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/plain")
    public void sendMessage(@RequestParam(value = "msg") String message) {
        messageService.sendMessage(message);
    }

}