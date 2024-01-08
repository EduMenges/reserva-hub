package com.reservahub.backend.app.reservationRequest;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRequestRepository
        extends JpaRepository<ReservationRequest, Long> {

    @Query("FROM ReservationRequest r " +
            "WHERE r.status = 'AWAITING_APPROVAL' " +
            "AND r.userId = :userId " +
            "ORDER BY r.date, r.startTime")
    public ArrayList<ReservationRequest>
            findRequestsWaitingApprovalByUserIdOrdered(
                        @Param("userId") Long userId);
            
}
