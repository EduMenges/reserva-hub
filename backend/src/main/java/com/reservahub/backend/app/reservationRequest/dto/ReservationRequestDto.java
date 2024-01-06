package com.reservahub.backend.app.reservationRequest.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
    private Long roomId;
    private String eventName;
    private String eventDescription;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
