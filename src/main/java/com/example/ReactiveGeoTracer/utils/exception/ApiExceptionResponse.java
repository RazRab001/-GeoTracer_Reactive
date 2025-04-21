package com.example.ReactiveGeoTracer.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ApiExceptionResponse {
    private final String message;
    private final Integer httpStatusCode;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiExceptionResponse(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatusCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
