package com.epam.training.ticketservice.core.screening.persistence.repository;

import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    void deleteByMovieAndRoomAndDate(String movie, String room, LocalDateTime date);

    List<Screening> findByRoom(String room);
}
