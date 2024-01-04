package com.reservahub.backend.app.reservation;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r " +
                "WHERE r.roomId = ?1 " +
                "AND r.date = ?2 " +
                "AND r.status = 'ACTIVE'")
    ArrayList<Reservation> findActiveReservationsByRoomIdAndDate(
            Long roomId, LocalDate date);

}
