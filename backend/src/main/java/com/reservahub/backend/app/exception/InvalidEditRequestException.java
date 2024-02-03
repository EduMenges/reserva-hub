package com.reservahub.backend.app.exception;

public class InvalidEditRequestException extends RuntimeException{
    public InvalidEditRequestException(String message) {
        super(message);
    }
}
