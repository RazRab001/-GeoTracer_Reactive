package com.example.ReactiveGeoTracer.utils.exception;

public class UncheckedException extends RuntimeException {
    public UncheckedException(Throwable throwable ) {
        super(throwable.getMessage(), throwable);
    }
}
