package me.heyner.hyperskill_cinema_rest_api.controllers;


import me.heyner.hyperskill_cinema_rest_api.models.Room;
import me.heyner.hyperskill_cinema_rest_api.models.Seat;
import me.heyner.hyperskill_cinema_rest_api.models.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RoomController {

    private final Room mainRoom = new Room(9, 9);

    @GetMapping(path="/seats")
    public Room getRoom() {
        return mainRoom;
    }

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody Seat requestedSeat) {
         Seat seat = mainRoom.getSeat(
                 requestedSeat.getRow(), requestedSeat.getColumn()
         ).orElseThrow(
                 () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!")
         );

         if(!seat.isAvailable()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ticket has been already purchased!");

         Ticket ticket = new Ticket(seat);

         seat.setTicket(ticket);

         return ticket;
    }

    @PostMapping("/return")
    public Map<String, Seat> returnTicket(@RequestBody Map<String, String> returnedToken) {

        if(returnedToken.get("token") == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The request should contain a valid token!");
        }

        Seat seat = mainRoom.getSeat(returnedToken.get("token")).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong token!")
        );

        seat.setTicket(null);

        Map<String, Seat> response = new LinkedHashMap<>();

        response.put("returned_ticket", seat);

        return response;

    }

    @GetMapping("/stats")
    public Map<String, Integer> getStatistics(@RequestParam(name="password", required = false) String password) {
        String PASSWORD = "super_secret";
        if(password ==null || !password.equals(PASSWORD)) throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The password is wrong!");

        int currentIncome = mainRoom.getSeats()
                .stream()
                .filter(seat -> !seat.isAvailable())
                .mapToInt(Seat::getPrice).sum();

        int availableSeats = (int) mainRoom.getSeats()
                .stream()
                .filter(Seat::isAvailable)
                .count();

        int purchasedTickets = (int) mainRoom.getSeats()
                .stream()
                .filter(seat -> !seat.isAvailable())
                .count();

        Map<String, Integer> response = new HashMap<>();

        response.put("current_income", currentIncome);

        response.put("number_of_available_seats", availableSeats);

        response.put("number_of_purchased_tickets", purchasedTickets);

        return response;
    }
}
