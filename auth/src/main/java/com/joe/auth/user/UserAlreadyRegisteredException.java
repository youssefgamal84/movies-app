package com.joe.auth.user;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException(String s) {
        super(s);
    }
}
