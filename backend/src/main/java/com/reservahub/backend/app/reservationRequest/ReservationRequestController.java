package com.reservahub.backend.app.reservationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            @RequestBody ReservationRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        reservationRequestService.emitRequest(user, request);

        return ResponseEntity.ok("Reservation created.");
    }
}
