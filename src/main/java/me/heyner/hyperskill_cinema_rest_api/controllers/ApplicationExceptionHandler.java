package me.heyner.hyperskill_cinema_rest_api.controllers;

import me.heyner.hyperskill_cinema_rest_api.exception.SimpleError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<SimpleError> handleException(ResponseStatusException e) {
        SimpleError error = new SimpleError(e.getReason());

        return new ResponseEntity<>(error, e.getStatusCode());
    }
}
