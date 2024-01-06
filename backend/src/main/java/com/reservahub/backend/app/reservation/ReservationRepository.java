package com.reservahub.backend.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository
                extends JpaRepository<Reservation, Long> {

        @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
                        "FROM Reservation r " +
                        "WHERE r.room.id = :roomId " +
                        "AND r.date = :date " +
                        "AND r.status = 'ACTIVE' " +
                        "AND ((r.startTime < :endTime AND r.endTime > :startTime) OR " +
                        "(r.startTime < :startTime AND r.endTime > :startTime) OR " +
                        "(r.startTime < :endTime AND r.endTime > :endTime))")
        boolean existsActiveReservationWithTimeConflict(
                        @Param("roomId") Long roomId,
                        @Param("date") LocalDate date,
                        @Param("startTime") LocalTime startTime,
                        @Param("endTime") LocalTime endTime);

}
