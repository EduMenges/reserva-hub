package com.reservahub.backend.app.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.reservation.dto.ChangeStatusReservationRequestDto;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/approve")
    public ResponseEntity<ReservationResponseDto> approveReservation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangeStatusReservationRequestDto changeStatusReservationRequest) {
        Reservation approvedReservation = reservationService.approveReservation(userDetails, changeStatusReservationRequest.getReservationId());

        return ResponseEntity.ok(new ReservationResponseDto(approvedReservation.getId(), approvedReservation.getEventName(), approvedReservation.getStatus().name()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deny")
    public ResponseEntity<ReservationResponseDto> denyReservation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangeStatusReservationRequestDto changeStatusReservationRequest) {
        Reservation deniedReservation = reservationService.denyReservation(userDetails, changeStatusReservationRequest.getReservationId());

        return ResponseEntity.ok(new ReservationResponseDto(deniedReservation.getId(), deniedReservation.getEventName(), deniedReservation.getStatus().name()));
    }

    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @PostMapping("/cancel")
    public ResponseEntity<ReservationResponseDto> cancelReservation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangeStatusReservationRequestDto changeStatusReservationRequest) {
        Reservation canceledReservation = reservationService.cancelReservation(userDetails, changeStatusReservationRequest.getReservationId());

        return ResponseEntity.ok(new ReservationResponseDto(canceledReservation.getId(), canceledReservation.getEventName(), canceledReservation.getStatus().name()));
    }
}
