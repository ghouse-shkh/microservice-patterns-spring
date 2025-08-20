package com.learn.booking.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AvailabilityResponse {
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private boolean available;
    private String message;
    private RoomDto room;

    public static AvailabilityResponse of(BookingRequest req, boolean available, String message, RoomDto room) {
    return AvailabilityResponse.builder()
            .roomId(req.getRoomId())
            .checkIn(req.getCheckInDate())
            .checkOut(req.getCheckOutDate())
            .available(available)
            .message(message)
            .room(room)
            .build();
    }
}