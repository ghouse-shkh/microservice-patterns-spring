package com.learn.booking.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.booking.model.Booking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookingRequest {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotBlank(message = "Guest name is required")
    private String guestName;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "At least one guest required")
    private int guests;

    @AssertTrue(message = "Check-out date must be after check-in date")
    @JsonIgnore
    @Schema(hidden = true)
    public boolean isValidDates() {
        if (checkInDate == null || checkOutDate == null) {
            return true; // let @NotNull handle nulls
        }
        return checkOutDate.isAfter(checkInDate);
    }

    public Booking toBooking() {
        return Booking.builder()
                .roomId(roomId)
                .guestName(guestName)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .guests(guests)
                .build();
    }
}