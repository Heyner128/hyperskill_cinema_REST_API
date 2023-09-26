package me.heyner.hyperskill_cinema_rest_api.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {
    private final int row;

    private final int column;

    @JsonIgnore
    private Ticket ticket;

    @JsonCreator
    public Seat(
            @JsonProperty("row") int row,
            @JsonProperty("column") int column
    ) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @JsonProperty("price")
    public int getPrice() {
        return row<=4?10:8;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return ticket == null;
    }
}
