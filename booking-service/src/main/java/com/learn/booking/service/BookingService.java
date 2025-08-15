package com.learn.booking.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.learn.booking.client.RoomClient;
import com.learn.booking.dto.AvailabilityResponse;
import com.learn.booking.dto.InstanceInfo;
import com.learn.booking.dto.RoomDto;
import com.learn.booking.dto.RoomsResponse;
import com.learn.booking.repository.BookingRepository;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepo;
    private final RoomClient roomClient;

    public InstanceInfo getRoomInstanceInfo() {
        return roomClient.getInstanceInfo();
    }

    @CircuitBreaker(name = "roomService", fallbackMethod = "fallbackAvailability")
    @Retry(name = "roomService")
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

    @CircuitBreaker(name = "roomService", fallbackMethod = "fallbackSearch")
    @Retry(name = "roomService")
    @TimeLimiter(name = "roomService")
    public CompletableFuture<RoomsResponse> searchRooms(String type, Long delayMs, Integer status) {
        log.info("Searching for rooms of type {}", type);
        return CompletableFuture.supplyAsync(() -> {
            List<RoomDto> rooms = roomClient.searchRooms(type, delayMs, status);
            return new RoomsResponse(rooms, null);
        });
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

    @SuppressWarnings("unused")
    private AvailabilityResponse fallbackAvailability(Long roomId, LocalDate checkIn, LocalDate checkOut, int guests,
            Throwable ex) {
        return response(roomId, checkIn, checkOut, false, "Room availability check failed. Please try later.");
    }

    @SuppressWarnings("unused")
    private CompletableFuture<RoomsResponse> fallbackSearch(String type, Long delayMs, Integer status, Throwable ex) {
        return CompletableFuture.completedFuture(
                new RoomsResponse(Collections.emptyList(), "RoomService unavailable: " + ex.getMessage()));
    }

}