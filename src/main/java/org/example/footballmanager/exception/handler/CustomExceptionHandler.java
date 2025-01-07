package org.example.footballmanager.exception.handler;

import org.example.footballmanager.exception.PlayerNotFoundException;
import org.example.footballmanager.exception.TeamNotFoundException;
import org.example.footballmanager.exception.TransferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String playerNotFoundException(PlayerNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String teamNotFoundException(TeamNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TransferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String transferNotFoundException(TransferNotFoundException e) {
        return e.getMessage();
    }
}