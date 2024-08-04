package com.example.Interview.exception;

public class UserCreditLimitNotFoundException  extends  RuntimeException {

    public UserCreditLimitNotFoundException(String  message) {
        super(message);
    }
}
