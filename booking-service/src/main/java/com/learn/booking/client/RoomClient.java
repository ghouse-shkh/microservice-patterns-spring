package com.learn.booking.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.learn.booking.dto.InstanceInfo;
import com.learn.booking.dto.RoomDto;

@FeignClient(name = "room-service", path = "/api/rooms")
public interface RoomClient {
    @GetMapping("/{id}")
    RoomDto getRoom(@PathVariable("id") Long id);

    @GetMapping("/instance-info")
    InstanceInfo getInstanceInfo();

    @GetMapping("/search")
    List<RoomDto> searchRooms(@RequestParam String type,
            @RequestParam(required = false) Long delayMs,
            @RequestParam(required = false) Integer status);
}