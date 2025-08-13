package com.learn.booking.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.learn.booking.dto.RoomDto;



@FeignClient(name = "room-service", path = "/api/rooms")
public interface RoomClient {
    @GetMapping("/{id}")
    RoomDto getRoom(@PathVariable("id") Long id);
}