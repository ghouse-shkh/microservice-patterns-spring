package com.learn.roomservice.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Room> getRoomById(Long id) {
        return repo.findById(id);
    }

    public List<Room> searchByType(String type) {
        return repo.findByTypeIgnoreCase(type);
    }

    // Simulate latency (ms)
    public void simulateDelay(Long delayMs) {
        if (delayMs != null && delayMs > 0) {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
