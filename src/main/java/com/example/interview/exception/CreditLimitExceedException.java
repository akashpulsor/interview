package com.example.Interview.exception;

public class CreditLimitExceedException   extends  RuntimeException {

    public CreditLimitExceedException(String  message) {
        super(message);
    }
}
