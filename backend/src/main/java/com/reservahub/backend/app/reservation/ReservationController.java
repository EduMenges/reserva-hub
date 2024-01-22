package com.reservahub.backend.app.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.reservation.dto.ReservationRequestDto;
import com.reservahub.backend.app.reservation.dto.ReservationResponseDto;
import com.reservahub.backend.app.user.UserDetails;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @PostMapping("/request")
    public ResponseEntity<ReservationResponseDto> saveReservation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ReservationRequestDto request) {
        Reservation emitedRequest = reservationService.saveReservation(userDetails, request);

        return ResponseEntity.ok(new ReservationResponseDto(emitedRequest.getId(), emitedRequest.getEventName(), emitedRequest.getStatus().name()));
    }
}
