package com.learn.roomservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.roomservice.model.Room;
import com.learn.roomservice.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repo;

    public List<Room> getAllRooms() {
        return repo.findAll();
    }

    public Room getRoomById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }
}
