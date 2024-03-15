package com.nata.bookspace.bookservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private HttpStatus status;
    private LocalDateTime timeStamp;
    private String message;

    public ErrorResponse(){
        timeStamp=LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
}