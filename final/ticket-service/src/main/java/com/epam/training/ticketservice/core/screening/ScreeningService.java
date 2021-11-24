package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;

import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    void createScreening(ScreeningDto screening);

    void deleteScreening(String screeningMovie, String screeningRoom, String screeningDate);

    List<ScreeningDto> getScreeningList();

}
