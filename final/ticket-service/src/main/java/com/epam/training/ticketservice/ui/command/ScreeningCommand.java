package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Optional;

@ShellComponent
public class ScreeningCommand {

    private final UserService userService;
    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describeAccount();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }

    public ScreeningCommand(UserService userService, ScreeningService screeningService, MovieService movieService, RoomService roomService) {
        this.userService = userService;
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    //create, delete, list,

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Create a new screening")
    public ScreeningDto createScreening(String movie, String room, String date){
        ScreeningDto screeningDto = ScreeningDto.builder()
                .withMovie(movie)
                .withRoom(room)
                .withDate(date)
                .build();
        screeningService.createScreening(screeningDto);
        return screeningDto;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Delete az existing screening.")
    public String deleteScreening(String movie, String room, String date){
        if(movieService.getMovieByName(movie).isEmpty() || roomService.getRoomByName(room).isEmpty()){
            return "Screening does not exist";
        }else{
            screeningService.deleteScreening(movie, room, date);
            return movie + " deleted successfully.";
        }
    }
}
