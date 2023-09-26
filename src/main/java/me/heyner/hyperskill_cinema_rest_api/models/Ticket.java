package me.heyner.hyperskill_cinema_rest_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {

    private final String token;

    private final Seat seat;

    public Ticket(Seat seat) {
        this.seat = seat;
        this.token = UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }

    @JsonProperty("ticket")
    public Seat getSeat() {
        return seat;
    }
}
