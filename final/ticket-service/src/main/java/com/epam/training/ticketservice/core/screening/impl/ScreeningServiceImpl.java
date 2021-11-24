package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScreeningServiceImpl  implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository,
                                MovieService movieService,
                                RoomService roomService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @Override
    public void createScreening(ScreeningDto screeningDto) {
        Objects.requireNonNull(screeningDto, "Screening cannot be null");
        Objects.requireNonNull(screeningDto.getMovie().getName(), "Movie title cannot be null");
        Objects.requireNonNull(screeningDto.getRoom().getName(), "Room name cannot be null");
        Objects.requireNonNull(screeningDto.getDate(), "Screening start date cannot be null");

        List<ScreeningDto> sameRoomScreenings = getScreeningsByRoomName(screeningDto.getRoom().getName());
        LocalDateTime newScreeningStart = screeningDto.getDate();

        for (var storedScreening : sameRoomScreenings) {
            LocalDateTime storedScreeningStart = storedScreening.getDate();
            int storedScreeningLength = storedScreening.getMovie().getLength();
            int newScreeningLength = screeningDto.getMovie().getLength();

            if (newScreeningStart.isAfter(storedScreeningStart.minusMinutes(newScreeningLength + 10))
                    && (newScreeningStart.isBefore(storedScreeningStart.plusMinutes(storedScreeningLength))
                    || newScreeningStart.isEqual(storedScreeningStart.plusMinutes(storedScreeningLength)))) {
                System.out.println("There is an overlapping screening");
                return;
            } else if (newScreeningStart.isAfter(storedScreeningStart.plusMinutes(storedScreeningLength))
                    && (newScreeningStart.isBefore(storedScreeningStart.plusMinutes(storedScreeningLength + 10))
                    || newScreeningStart.isEqual(storedScreeningStart.plusMinutes(storedScreeningLength + 10)))) {
                System.out.println("This would start in the break period after another screening in this room");
                return;
            }
        }

        Screening screening = new Screening(
                screeningDto.getMovie().getName(),
                screeningDto.getRoom().getName(),
                screeningDto.getDate());
        screeningRepository.save(screening);
    }

    @Override
    public void deleteScreening(String movie, String room, LocalDateTime date) {
        screeningRepository.deleteByMovieAndRoomAndDate(movie, room, date);
    }

    private List<ScreeningDto> getScreeningsByRoomName(String room) {
        return screeningRepository.findByRoom(room)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private ScreeningDto convertEntityToDto(Screening screening) {
        Optional<MovieDto> movieDto = movieService.getMovieByName(screening.getMovie());
        Optional<RoomDto> roomDto = roomService.getRoomByName(screening.getRoom());

        if (movieDto.isEmpty() || roomDto.isEmpty()) {
            throw new NullPointerException("The Movie or Room is non-existent or the date is incorrect.");
        }
        return ScreeningDto.builder()
            .withMovie(movieDto.get())
            .withRoom(roomDto.get())
            .withDate(screening.getDate())
            .build();
    }
}
