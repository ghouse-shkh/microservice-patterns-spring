package com.learn.booking.messaging.events;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.learn.booking.model.Booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingCreatedEvent {
    private Long bookingId;
    private Long roomId;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;
    private BigDecimal totalPrice;

    public static BookingCreatedEvent from(Booking booking) {
        return BookingCreatedEvent.builder()
                .bookingId(booking.getId())
                .roomId(booking.getRoomId())
                .guestName(booking.getGuestName())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .guests(booking.getGuests())
                .totalPrice(booking.getTotalPrice())
                .build();
    }
}