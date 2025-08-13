package com.learn.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.booking.model.Booking;
import com.learn.booking.repository.BookingRepository;

@Configuration
public class BookingDataLoader {
    @Bean
    CommandLineRunner loadData(BookingRepository repo) {

        return args -> 

            repo.saveAll(List.of(
                Booking.builder().roomId(101L).guestName("Alice").checkInDate(LocalDate.now().plusDays(1)).checkOutDate(LocalDate.now().plusDays(3)).guests(2).totalPrice(400).build(),
                Booking.builder().roomId(102L).guestName("Bob").checkInDate(LocalDate.now().plusDays(2)).checkOutDate(LocalDate.now().plusDays(5)).guests(4).totalPrice(1050).build(),
                Booking.builder().roomId(103L).guestName("Charlie").checkInDate(LocalDate.now().plusDays(3)).checkOutDate(LocalDate.now().plusDays(4)).guests(2).totalPrice(120).build(),
                Booking.builder().roomId(101L).guestName("David").checkInDate(LocalDate.now().plusDays(5)).checkOutDate(LocalDate.now().plusDays(7)).guests(2).totalPrice(400).build(),
                Booking.builder().roomId(102L).guestName("Eve").checkInDate(LocalDate.now().plusDays(7)).checkOutDate(LocalDate.now().plusDays(10)).guests(3).totalPrice(1050).build()
            ));
        
    }
}

