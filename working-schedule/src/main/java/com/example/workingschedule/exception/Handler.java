package com.example.workingschedule.exception;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Void> invalidTokenException(
            InvalidTokenException ex, WebRequest request) {

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
