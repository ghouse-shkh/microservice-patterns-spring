package com.learn.booking.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.booking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
            Long roomId, LocalDate checkOut, LocalDate checkIn);
}