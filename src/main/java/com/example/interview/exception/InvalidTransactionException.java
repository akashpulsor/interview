package com.example.Interview.exception;

public class InvalidTransactionException  extends  RuntimeException {

    public InvalidTransactionException(String  message) {
        super(message);
    }
}
