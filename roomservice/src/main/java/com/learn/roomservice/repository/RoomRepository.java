package com.learn.roomservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.roomservice.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}