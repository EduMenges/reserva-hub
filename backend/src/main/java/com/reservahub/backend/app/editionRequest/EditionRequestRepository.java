package com.reservahub.backend.app.editionRequest;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditionRequestRepository
        extends JpaRepository<EditionRequest, Long> {

    @Query("FROM EditionRequest e " +
            "JOIN e.reservation r " +
            "WHERE e.status = 'AWAITING_APPROVAL' " +
            "AND r.userId = :userId " +
            "ORDER BY e.date, e.startTime")
    public ArrayList<EditionRequest>
            findEditionsWaitingApprovalByUserIdOrdered(
                    @Param("userId") Long userId);
    
}
