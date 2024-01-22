package com.reservahub.backend.app.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDto {
    private Long reservationId;
    private String eventName;
    private String status;
}
