package com.learn.booking.dto;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityResponse {
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private boolean available;
    private String message;
    private RoomDto room;
}