package com.epam.training.ticketservice.core.room.persistance.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private Integer rows;
    private Integer columns;

    public Room(){}

    public Room(String name, Integer rows, Integer columns){

        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
    public Integer getRows() {
        return rows;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }
    public Integer getColumns() {
        return columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return rows == room.rows && columns == room.columns
                && Objects.equals(id, room.id) && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rows, columns);
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name='" + name + '\'' + ", rows="
                + rows + ", columns=" + columns + '}';
    }
}

