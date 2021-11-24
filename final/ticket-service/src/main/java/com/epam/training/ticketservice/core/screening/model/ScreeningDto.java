package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ScreeningDto {

    private final MovieDto movie;
    private final RoomDto room;
    private final LocalDateTime date;

    public ScreeningDto(MovieDto movie, RoomDto room, LocalDateTime date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public static Builder builder() {
        return new Builder();
    }

    public MovieDto getMovie() {
        return movie;
    }

    public RoomDto getRoom() {
        return room;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScreeningDto that = (ScreeningDto) o;
        return Objects.equals(movie, that.movie) && Objects.equals(room, that.room) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, room, date);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String screeningStartDateString = date.format(formatter);
        return movie.toString() + ", screened in room " + room.getName() + ", at " + screeningStartDateString;
    }

    public static class Builder {
        private MovieDto movie;
        private RoomDto room;
        private LocalDateTime date;

        public ScreeningDto.Builder withMovie(MovieDto movie) {
            this.movie = movie;
            return this;
        }

        public ScreeningDto.Builder withRoom(RoomDto room) {
            this.room = room;
            return this;
        }

        public ScreeningDto.Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(movie, room, date);
        }
    }
}

