package com.learn.roomservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.learn.roomservice.model.Room;
import com.learn.roomservice.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Long id) {
        // Simulation of error for demonstrating fault tolerance
        if (id == 9999) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }

        var optionalRoom = roomService.getRoomById(id);
        return optionalRoom
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Room>> search(@RequestParam String type,
            @RequestParam(required = false) Long delayMs,
            @RequestParam(required = false) Integer status) {

        // Simulation of latency,error for demonstrating fault tolerance
        if (delayMs != null) {
            log.info("Simulating latency of {} ms", delayMs);
            roomService.simulateDelay(delayMs);
        }
        if (status != null && status >= 400) {
            return ResponseEntity.status(status).build();
        }

        return ResponseEntity.ok(roomService.searchByType(type));
    }
}