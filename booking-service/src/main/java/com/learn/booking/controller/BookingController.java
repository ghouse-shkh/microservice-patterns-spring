package com.learn.booking.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.booking.dto.AvailabilityResponse;
import com.learn.booking.messaging.BookingEventPublisher;
import com.learn.booking.messaging.events.BookingCreatedEvent;
import com.learn.booking.model.Booking;
import com.learn.booking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/availability")
    public AvailabilityResponse isAvailable(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(defaultValue = "1") int guests) {
        return bookingService.checkAvailability(roomId, checkIn, checkOut, guests);
    }

   private BookingCreatedEvent buildEvent(Booking booking) {
        return BookingCreatedEvent.builder()
                .id(booking.getId())
                .roomId(booking.getRoomId())
                .guestName(booking.getGuestName())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .guests(booking.getGuests())
                .totalPrice(booking.getTotalPrice())
                .build();
    }
}