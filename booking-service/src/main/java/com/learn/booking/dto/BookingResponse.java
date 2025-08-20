package com.learn.booking.dto;

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
@Builder(toBuilder = true)
public class BookingResponse {
    private Long id;
    private Long roomId;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;
    private BigDecimal totalPrice;
    private String status;

    public static BookingResponse fromBooking(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .roomId(booking.getRoomId())
                .guestName(booking.getGuestName())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .guests(booking.getGuests())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .build();
    }
}
