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
                        "JOIN e.user u " +
                        "WHERE e.status = 'AWAITING_APPROVAL' " +
                        "AND u.id = :userId " +
                        "ORDER BY e.date, e.startTime")
        public ArrayList<EditionRequest> findEditionsWaitingApprovalByUserIdOrdered(
                        @Param("userId") Long userId);

        @Query("SELECT e FROM EditionRequest e " +
                        "JOIN FETCH e.reservation " +
                        "JOIN FETCH e.user " +
                        "JOIN FETCH e.room " +
                        "ORDER BY " +
                        "e.date " +
                        "DESC")
        public ArrayList<EditionRequest> findAllEditions();

}
