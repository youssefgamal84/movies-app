package com.joe.movies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> genericNotFound(EntityNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(new ErrorMessage("Not found"), HttpStatus.NOT_FOUND);
    }
}
