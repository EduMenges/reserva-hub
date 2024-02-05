package com.reservahub.backend.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
        public boolean existsActiveReservationWithTimeConflict(
                        @Param("roomId") Long roomId,
                        @Param("date") LocalDate date,
                        @Param("startTime") LocalTime startTime,
                        @Param("endTime") LocalTime endTime);

        @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
                        "FROM Reservation r " +
                        "WHERE r.room.id = :roomId " +
                        "AND r.date = :date " +
                        "AND r.status = 'ACTIVE' " +
                        "AND r.user.role = 'TEACHER' " +
                        "AND ((r.startTime < :endTime AND r.endTime > :startTime) OR " +
                        "(r.startTime < :startTime AND r.endTime > :startTime) OR " +
                        "(r.startTime < :endTime AND r.endTime > :endTime))")
        public boolean existsActiveReservationFromATeacherWithTimeConflict(
                        @Param("roomId") Long roomId,
                        @Param("date") LocalDate date,
                        @Param("startTime") LocalTime startTime,
                        @Param("endTime") LocalTime endTime);

        @Query("FROM Reservation r " +
                        "WHERE r.user.id = :userId " +
                        "ORDER BY " +
                        "CASE r.status " +
                        "WHEN 'ACTIVE' THEN 1 " +
                        "WHEN 'CANCELED' THEN 2 " +
                        "WHEN 'EXPIRED' THEN 3 " +
                        "ELSE 0 " +
                        "END, " +
                        "r.date, " +
                        "r.startTime")

        public ArrayList<Reservation> findReservationsByUserIdOrdered(
                        @Param("userId") Long userId);

        @Query("FROM Reservation r " +
                        "WHERE r.status = 'AWAITING_APPROVAL' " +
                        "AND r.user.id = :userId " +
                        "ORDER BY r.date, r.startTime")
        public ArrayList<Reservation> findRequestsWaitingApprovalByUserIdOrdered(
                        @Param("userId") Long userId);

        @Query("SELECT r FROM Reservation r " +
                        "JOIN FETCH r.user " +
                        "JOIN FETCH r.room " +
                        "ORDER BY " +
                        "r.date " +
                        "DESC")
        public ArrayList<Reservation> findAllReservations();

        @Query("SELECT r FROM Reservation r " +
                        "JOIN FETCH r.user " +
                        "JOIN FETCH r.room " +
                        "WHERE r.room.id = :roomId " +
                        "AND r.date = :date " +
                        "AND r.status = 'ACTIVE' " +
                        "AND ((r.startTime < :endTime AND r.endTime > :startTime) OR " +
                        "(r.startTime < :startTime AND r.endTime > :startTime) OR " +
                        "(r.startTime < :endTime AND r.endTime > :endTime))")
        public ArrayList<Reservation> findConflictingReservations(Long roomId, LocalDate date, LocalTime startTime,
                        LocalTime endTime);
}
