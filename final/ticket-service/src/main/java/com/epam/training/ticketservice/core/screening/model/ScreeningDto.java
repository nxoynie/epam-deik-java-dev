package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;

import java.util.Date;
import java.util.Objects;

public class ScreeningDto {

    private final Movie movieDto;
    private final Room roomDto;
    private final Date date;

    public ScreeningDto(Movie movieDto, Room roomDto, Date date) {
        this.movieDto = movieDto;
        this.roomDto = roomDto;
        this.date = date;
    }


    public static Builder builder() {
        return new Builder();
    }

    public Movie getMovieDto() {
        return movieDto;
    }

    public Room getRoomDto() {
        return roomDto;
    }

    public Date getDate() {
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
        return Objects.equals(movieDto, that.movieDto) && Objects.equals(roomDto, that.roomDto) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieDto, roomDto, date);
    }

    @Override
    public String toString() {
        return "ScreeningDto{"
                + "movie=" + movieDto
                + ", room=" + roomDto
                + ", date=" + date
                + '}';
    }
    public static class Builder {
        private Movie movieDto;
        private Room roomDto;
        private Date date;

        public ScreeningDto.Builder withMovie(Movie movieDto) {
            this.movieDto = movieDto;
            return this;
        }

        public ScreeningDto.Builder withRoom(Room roomDto) {
            this.roomDto = roomDto;
            return this;
        }
        public ScreeningDto.Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(movieDto, roomDto, date);
        }
    }
}
