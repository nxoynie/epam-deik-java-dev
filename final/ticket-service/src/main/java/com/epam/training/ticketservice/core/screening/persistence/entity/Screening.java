package com.epam.training.ticketservice.core.screening.persistence.entity;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;

import javax.persistence.*;
import java.util.Objects;
@Entity
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    public Movie movie;
    @ManyToOne
    private Room room;
    private String date;


    public Screening(Movie movie, Room room, String date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Screening screening = (Screening) o;
        return Objects.equals(id, screening.id) && Objects.equals(movie, screening.movie)
                && Objects.equals(room, screening.room) && Objects.equals(date, screening.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, room, date);
    }

    @Override
    public String toString() {
        return "Screening{" + "id=" + id + ", movie=" + movie + ", room=" + room
                + ", date=" + date + '}';
    }
}

