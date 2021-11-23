package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.util.List;
import java.util.Optional;

public interface ScreeningService {
    List<ScreeningDto> getScreeningList();

    Optional<ScreeningDto> getScreeningByMovieAndRoomAndDate(String screeningMovie, String screeningRoom, String screeningDate);

    void createScreening(ScreeningDto screening);

    void deleteScreening(String screeningMovie, String screeningRoom, String screeningDate);
}
