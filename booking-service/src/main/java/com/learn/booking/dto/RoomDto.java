package com.learn.booking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
public class RoomDto {
        private Long id;
        private String type;
        private BigDecimal pricePerNight;
        private int capacity;
        private String amenities;
    }
