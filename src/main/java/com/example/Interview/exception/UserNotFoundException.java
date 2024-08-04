package com.example.Interview.exception;

public class UserNotFoundException   extends  RuntimeException {

    public UserNotFoundException(String  message) {
        super(message);
    }
}
