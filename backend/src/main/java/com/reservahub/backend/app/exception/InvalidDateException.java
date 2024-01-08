package com.reservahub.backend.app.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Invalid date.");
    }
}
