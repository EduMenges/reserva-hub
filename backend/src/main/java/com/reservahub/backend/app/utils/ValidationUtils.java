package com.reservahub.backend.app.utils;

import com.reservahub.backend.app.exception.InvalidDateException;
import com.reservahub.backend.app.exception.InvalidEventDurationException;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.security.access.AccessDeniedException;

public class ValidationUtils {
     public static void validateEventDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidDateException();
        }
    }

    public static void validateEventDuration(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidEventDurationException();
        }
    }

    public static void validateAuthencity(Long requestId, Long originalId) {
        if (!requestId.equals(originalId)) {
            throw new AccessDeniedException("Acess Denied");
        }
    }
}
