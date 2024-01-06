package com.reservahub.backend.app.reservationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.reservationRequest.dto.ReservationRequestDto;
import com.reservahub.backend.app.user.UserDetails;

@RestController
@RequestMapping("/reservation-request")
public class ReservationRequestController {

    @Autowired
    ReservationRequestService reservationRequestService;

    @PostMapping("/send")
    public ResponseEntity<ReservationRequest> emitRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ReservationRequestDto request) {
        ReservationRequest emitedRequest = reservationRequestService.emitRequest(userDetails, request);

        return ResponseEntity.ok(emitedRequest);
    }
}
