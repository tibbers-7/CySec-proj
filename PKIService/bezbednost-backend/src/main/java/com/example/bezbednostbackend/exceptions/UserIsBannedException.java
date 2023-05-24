package com.example.bezbednostbackend.exceptions;

public class UserIsBannedException extends Exception{
    public UserIsBannedException(String message) {
        super(message);
    }
}
