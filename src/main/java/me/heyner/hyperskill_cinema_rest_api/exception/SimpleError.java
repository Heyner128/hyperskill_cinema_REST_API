package me.heyner.hyperskill_cinema_rest_api.exception;

public class SimpleError  {
    private final String error;

    public SimpleError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }


}
