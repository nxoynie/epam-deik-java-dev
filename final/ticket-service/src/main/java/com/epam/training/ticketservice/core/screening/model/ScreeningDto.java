package com.epam.training.ticketservice.core.screening.model;

import java.util.Objects;

public class ScreeningDto {

    private final String movie;
    private final String room;
    private final String date;

    public ScreeningDto(String movie, String room, String date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }


    public static Builder builder() {
        return new Builder();
    }

    public String getMovie() {
        return movie;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
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
        return "ScreeningDto{"
                + "movie=" + movie
                + ", room=" + room
                + ", date=" + date
                + '}';
    }
    public static class Builder {
        private String movie;
        private String room;
        private String date;

        public Builder withMovie(String movie) {
            this.movie = movie;
            return this;
        }

        public Builder withRoom(String room) {
            this.room = room;
            return this;
        }
        public ScreeningDto.Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(movie, room, date);
        }
    }
}
