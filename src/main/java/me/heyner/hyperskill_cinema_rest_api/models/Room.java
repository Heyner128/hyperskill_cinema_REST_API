package me.heyner.hyperskill_cinema_rest_api.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collections;

@JsonPropertyOrder({
    "total_rows",
    "total_columns",
    "available_seats"
})
public class Room {
    private final List<Seat> seats  = Collections.synchronizedList(new ArrayList<>());

    private final int rows;
    private final int columns;

    public Room(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                seats.add(new Seat(i + 1, j + 1));
            }
        }
    }

    @JsonProperty("available_seats")
    public List<Seat> getAvailableSeats() {

        return seats
                .stream()
                .filter(Seat::isAvailable)
                .toList();
    }

    @JsonIgnore
    public List<Seat> getSeats() {
        return seats;
    }

    public Optional<Seat> getSeat(int row, int column) {
        return seats
                .stream()
                .filter(seat -> seat.getRow() == row && seat.getColumn() == column)
                .findFirst();
    }

    public Optional<Seat> getSeat(String tokenUuid) {
        return seats
                .stream()
                .filter(seat -> seat.getTicket() != null && Objects.equals(seat.getTicket().getToken(), tokenUuid))
                .findFirst();
    }

    @JsonProperty("total_rows")
    public int getRows() {
        return rows;
    }

    @JsonProperty("total_columns")
    public int getColumns() {
        return columns;
    }
}
