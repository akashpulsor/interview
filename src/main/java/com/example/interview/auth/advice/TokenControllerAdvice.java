package com.example.Interview.auth.advice;


import com.example.Interview.exception.InvalidTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class TokenControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = InvalidTransactionException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage handleTokenRefreshException(InvalidTransactionException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ AuthenticationException.class })
    public ErrorMessage handleAuthenticationException(Exception ex, WebRequest request) {

        return new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }


}
