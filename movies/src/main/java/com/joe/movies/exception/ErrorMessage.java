package com.joe.movies.exception;

import java.util.Date;

public class ErrorMessage {

    private String message;
    private Date timestamp;

    public ErrorMessage(String message) {
        this.message = message;
        this.timestamp = new Date();
    }

    public ErrorMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
