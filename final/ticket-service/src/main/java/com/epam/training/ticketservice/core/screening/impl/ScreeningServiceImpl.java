package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.room.persistance.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
public class ScreeningServiceImpl  implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    String pattern = "YYYY-MM-DD hh:mm";
    DateFormat dateFormat = new SimpleDateFormat(pattern);

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ScreeningDto> getScreeningByMovieAndRoomAndDate(String screeningMovie, String screeningRoom, String screeningDate) {
        return convertEntityToDto(screeningRepository.findByMovieAndRoomAndDate(screeningMovie,screeningRoom,screeningDate));
    }

    @Override
    public void createScreening(ScreeningDto screeningDto) {
        Objects.requireNonNull(screeningDto, "Screening cannot be null.");
        Objects.requireNonNull(screeningDto.getMovieDto(), "Screening movie cannot be null.");
        Objects.requireNonNull(screeningDto.getRoomDto(),"Screening room cannot be null.");
        Objects.requireNonNull(screeningDto.getDate(), "Screening date cannot be null.");
        Movie movie = movieRepository.findByName(screeningDto.getMovieDto().getName())
                .orElseThrow(() -> new IllegalArgumentException("The given movie does not exist"));
        Room room = roomRepository.findByName(screeningDto.getRoomDto().getName())
                .orElseThrow(() -> new IllegalArgumentException("The given room does not exist"));
        Screening screening = new Screening(screeningDto.getMovieDto(), screeningDto.getRoomDto(), screeningDto.getDate());
        if (isRoomEmpty(screening)) {
            screeningRepository.save(screening);
        }
    }

    private boolean isRoomEmpty(Screening screening) {
        List<ScreeningDto> screeningList = screeningRepository
                .findAll().stream().map(this::convertEntityToDto)
                .filter(sc -> sc.getRoomDto().getName().equals(screening.getRoom().getName()))
                .collect(Collectors.toList());
        for (ScreeningDto screeningDto : screeningList) {
            int movieLength = screeningDto.getMovieDto().getLength();
            Date screeningBegin = screeningDto.getDate();
            Date screeningEnd = screeningBegin;
            Date screeningEndAndBreak = screeningBegin;
            screeningEnd = DateUtils.addMinutes(screeningEnd, movieLength);
            screeningEndAndBreak = DateUtils.addMinutes(screeningEndAndBreak, movieLength + 10);
            if (screeningBegin.compareTo(screening.getDate()) <= 0 && screening.getDate().compareTo(screeningEnd) < 0) {
                System.out.println("There is an overlapping screening");
                return false;
            }
            if (screeningBegin.compareTo(screening.getDate()) <= 0
                    && screening.getDate().compareTo(screeningEndAndBreak) <= 0) {
                System.out.println("This would start in the break period after another screening in this room");
                return false;
            }
        }
        return true;
    }

    @Override
    public void deleteScreening(String screeningMovie, String screeningRoom, String screeningDate ) {
        Objects.requireNonNull(screeningMovie, "Screening movie cannot be null.");
        Objects.requireNonNull(screeningRoom, "Screening room cannot be null.");
        Objects.requireNonNull(screeningDate, "Screening date cannot be null.");
        screeningRepository.deleteByMovieAndRoomAndDate(screeningMovie, screeningRoom, screeningDate);
    }
    private ScreeningDto convertEntityToDto(Screening screening) {
        return ScreeningDto.builder()
                .withMovie(screening.getMovie())
                .withRoom(screening.getRoom())
                .withDate(screening.getDate())
                .build();
    }


    private Optional<ScreeningDto> convertEntityToDto(Optional<Screening> screening) {
        return screening.isEmpty() ? Optional.empty() : Optional.of(convertEntityToDto(screening.get()));
    }
}
