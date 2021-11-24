package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
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
    private final MovieService movieService;
    private final RoomService roomService;
    String pattern = "YYYY-MM-DD hh:mm";
    DateFormat dateFormat = new SimpleDateFormat(pattern);

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository, MovieService movieService, RoomService roomService) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.movieService = movieService;
        this.roomService = roomService;
    }


    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }


    @Override
    public void createScreening(ScreeningDto screeningDto) {
        Objects.requireNonNull(screeningDto, "ScreeningDto cannot be null");
        Objects.requireNonNull(screeningDto.getMovie(), "Movie cannot be null");
        Objects.requireNonNull(screeningDto.getRoom(), "Room cannot be null");
        Objects.requireNonNull(screeningDto.getDate(), "Date cannot be null");
        Movie movie = movieRepository.findByName(screeningDto.getMovie())
                .orElseThrow(() -> new IllegalArgumentException("The given movie does not exist"));
        Room room = roomRepository.findByName(screeningDto.getRoom())
                .orElseThrow(() -> new IllegalArgumentException("The given room does not exist"));
        Screening screening = new Screening(movie,room, screeningDto.getDate());
        screeningRepository.save(screening);
    }



    @Override
    public void deleteScreening(String screeningMovie, String screeningRoom, String screeningDate ) {
        Objects.requireNonNull(screeningMovie, "Screening movie cannot be null.");
        Objects.requireNonNull(screeningRoom, "Screening room cannot be null.");
        Objects.requireNonNull(screeningDate, "Screening date cannot be null.");
        screeningRepository.deleteByMovieAndRoomAndDate(screeningMovie, screeningRoom, screeningDate);
    }
    private ScreeningDto convertEntityToDto(Screening screening) {
    Optional<MovieDto> movieDto = movieService.getMovieByName(screening.getMovie().getName());
    Optional <RoomDto> roomDto = roomService.getRoomByName(screening.getRoom().getName());

    if (movieDto.isEmpty() || roomDto.isEmpty()){
        throw new NullPointerException("hiba");
        }
    return ScreeningDto.builder()
            .withMovie(screening.getMovie().getName())
            .withRoom(screening.getRoom().getName())
            .withDate(String.valueOf(screening.getDate()))
            .build();

    }
}
