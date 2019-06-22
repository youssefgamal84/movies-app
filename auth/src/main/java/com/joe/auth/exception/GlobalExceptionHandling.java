package com.joe.auth.exception;

import com.joe.auth.user.UserAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        ArrayList<ErrorMessage> listOfMessages = new ArrayList<>();
        for (ObjectError errorMessage : ex.getBindingResult().getAllErrors()) {
            listOfMessages.add(new ErrorMessage(errorMessage.getDefaultMessage()));
        }
        return new ResponseEntity<>(listOfMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> userAlreadyRegistered(UserAlreadyRegisteredException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> genericException(Exception ex, WebRequest req) {
        return new ResponseEntity<>(new ErrorMessage("Unknown Error Happened"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
