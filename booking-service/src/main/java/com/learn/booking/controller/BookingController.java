package com.learn.booking.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.booking.dto.AvailabilityResponse;
import com.learn.booking.dto.BookingRequest;
import com.learn.booking.dto.BookingResponse;
import com.learn.booking.exceptions.RoomNotAvailableException;
import com.learn.booking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/availability")
    public AvailabilityResponse isAvailable(
            @RequestBody BookingRequest bookingRequest) {
        return bookingService.checkAvailability(bookingRequest);
    }

    @PostMapping
    public BookingResponse bookRoom(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookRoom(bookingRequest);
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleRoomNotAvailable(RoomNotAvailableException ex) {
        return Map.of("error", ex.getMessage());
    }
}