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

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Long id) {
        var room = roomService.getRoomById(id);
        if(room == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        return room;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Room>> search(@RequestParam String type) {
       return ResponseEntity.ok(roomService.searchByType(type));
    }
}