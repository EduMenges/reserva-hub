package com.reservahub.backend.app.exception;

public class RoomAlreadyReservedException extends RuntimeException {
    public RoomAlreadyReservedException() {
        super("Room already reserved");
    }
}