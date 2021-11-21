package com.epam.training.ticketservice.core.room.persistance.repository;

import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByName(String name);

    Integer deleteByName(String name);
}
