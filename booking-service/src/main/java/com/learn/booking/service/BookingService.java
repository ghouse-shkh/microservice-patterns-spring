package com.learn.booking.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.learn.booking.client.RoomClient;
import com.learn.booking.dto.AvailabilityResponse;
import com.learn.booking.dto.BookingRequest;
import com.learn.booking.dto.BookingResponse;
import com.learn.booking.dto.RoomDto;
import com.learn.booking.exceptions.RoomNotAvailableException;
import com.learn.booking.model.Booking;
import com.learn.booking.repository.BookingRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepo;
    private final RoomClient roomClient;

    public BookingResponse bookRoom(BookingRequest req) {
        var availability = checkAvailability(req);
        if (!availability.isAvailable()) {
            throw new RoomNotAvailableException(availability.getMessage());
        }
        var room = availability.getRoom();
        var savedBooking = saveBooking(req, room);
        return BookingResponse.fromBooking(savedBooking);
    }

    private Booking saveBooking(BookingRequest req, RoomDto room) {
        var booking = req.toBooking();
        long nights = ChronoUnit.DAYS.between(req.getCheckInDate(), req.getCheckOutDate());
        booking.setTotalPrice(room.getPricePerNight().multiply(
                BigDecimal.valueOf(nights)));
        booking.setStatus("CONFIRMED");

        return bookingRepo.save(booking);
    }

    public AvailabilityResponse checkAvailability(BookingRequest req) {

        RoomDto room;
        try {
            room = roomClient.getRoom(req.getRoomId());
        } catch (FeignException.NotFound ex) {
            throw new RoomNotAvailableException("Room not found");
        }

        if (req.getGuests() > room.getCapacity()) {
            throw new RoomNotAvailableException("Room capacity exceeded");
        }

        boolean available = isRoomAvailable(req);
        String message = available ? "Room available" : "Room not available";
        return AvailabilityResponse.of(req, available, message, room);
    }

    private boolean isRoomAvailable(BookingRequest request) {
        return !bookingRepo
                .existsByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(request.getRoomId(),
                        request.getCheckOutDate(), request.getCheckInDate());

    }

}