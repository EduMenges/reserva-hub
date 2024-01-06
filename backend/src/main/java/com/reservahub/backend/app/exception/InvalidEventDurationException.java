package com.reservahub.backend.app.exception;

public class InvalidEventDurationException extends RuntimeException {
    public InvalidEventDurationException() {
        super("Invalid event duration.");
    }
}