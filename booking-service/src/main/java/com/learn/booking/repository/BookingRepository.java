package com.learn.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.booking.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
            Long roomId, LocalDate checkOut, LocalDate checkIn);
}
