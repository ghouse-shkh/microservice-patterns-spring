package com.learn.roomservice;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.roomservice.model.Room;
import com.learn.roomservice.repository.RoomRepository;

@Configuration
public class RoomDataLoader {
    @Bean
    CommandLineRunner seed(RoomRepository repo) {
        return args -> {
            repo.saveAll(List.of(
                    Room.builder().id(101L).type("Deluxe").pricePerNight(new BigDecimal("200.00")).capacity(2)
                            .amenities("WiFi,TV,AC").build(),
                    Room.builder().id(102L).type("Suite").pricePerNight(new BigDecimal("350.00")).capacity(4)
                            .amenities("WiFi,TV,AC,MiniBar").build(),
                    Room.builder().id(103L).type("Standard").pricePerNight(new BigDecimal("120.00")).capacity(2)
                            .amenities("WiFi,TV").build()));
        };
    }
}