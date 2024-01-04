package com.reservahub.backend.app.reservationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.user.UserDetails;

@RestController
@RequestMapping("/request")
public class ReservationRequestController {
    
    @Autowired
    ReservationRequestService reservationRequestService;

    @PostMapping("/send")
    public ResponseEntity<String> emitRequest(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ReservationRequestDTO request) {
        reservationRequestService.emitRequest(user, request);

        return ResponseEntity.ok("Reservation created.");
    }
}
