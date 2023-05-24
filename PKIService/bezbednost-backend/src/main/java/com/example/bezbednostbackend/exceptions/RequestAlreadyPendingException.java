package com.example.bezbednostbackend.exceptions;

public class RequestAlreadyPendingException extends Exception{
    public RequestAlreadyPendingException(String message) {
        super(message);
    }
}
