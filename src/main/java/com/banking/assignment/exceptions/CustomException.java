package com.banking.assignment.exceptions;

import org.springframework.http.HttpStatus;

@lombok.Getter
public class CustomException extends RuntimeException {
    private String action;
    private HttpStatus httpStatus;


    public CustomException(String message, String action, HttpStatus httpStatus) {
        super(message);
        this.action = action;
        this.httpStatus = httpStatus;
    }
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
