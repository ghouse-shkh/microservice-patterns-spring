package com.learn.booking.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.learn.booking.client.RoomClient;
import com.learn.booking.dto.AvailabilityResponse;
import com.learn.booking.dto.InstanceInfo;
import com.learn.booking.dto.RoomDto;
import com.learn.booking.repository.BookingRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepo;
    private final RoomClient roomClient;

    public AvailabilityResponse checkAvailability(Long roomId, LocalDate checkIn, LocalDate checkOut, int guests) {
        if (!checkOut.isAfter(checkIn))
            return response(roomId, checkIn, checkOut, false, "Check-out date must be after check-in date");

        RoomDto room;
        try {
            room = roomClient.getRoom(roomId);
        } catch (FeignException.NotFound ex) {
            return response(roomId, checkIn, checkOut, false, "Room not found");
        }

        if (guests > room.getCapacity()) {
            return response(roomId, checkIn, checkOut, false, "Room capacity exceeded");
        }

        boolean available = isRoomAvailable(roomId, checkIn, checkOut);
        String message = available ? "Room available" : "Room not available";
        return response(roomId, checkIn, checkOut, available, message, room);
    }

    private boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return bookingRepo
                .findByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(roomId, checkOut, checkIn)
                .isEmpty();
    }

    private AvailabilityResponse response(Long roomId, LocalDate checkIn, LocalDate checkOut, boolean available,
            String message, RoomDto room) {
        var builder = AvailabilityResponse.builder()
                .roomId(roomId)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .available(available)
                .message(message);

        if (available && room != null) {
            builder.room(room);
        }

        return builder.build();
    }

    private AvailabilityResponse response(Long roomId, LocalDate checkIn, LocalDate checkOut,
            boolean available, String message) {
        return response(roomId, checkIn, checkOut, available, message, null);
    }
}