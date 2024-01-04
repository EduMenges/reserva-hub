package com.reservahub.backend.app.reservationRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRequestRepository
        extends JpaRepository<ReservationRequest, Long> {
    
}
