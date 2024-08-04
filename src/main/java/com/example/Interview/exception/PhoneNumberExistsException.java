package com.example.Interview.exception;

public class PhoneNumberExistsException   extends  RuntimeException {

    public PhoneNumberExistsException(String  message) {
        super(message);
    }
}
