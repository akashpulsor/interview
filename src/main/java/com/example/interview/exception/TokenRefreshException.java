package com.example.Interview.exception;

public class TokenRefreshException   extends  RuntimeException {

    public TokenRefreshException(String refreshToken, String  message) {
        super(message);
    }
}
