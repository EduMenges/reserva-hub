package com.reservahub.backend.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservahub.backend.app.room.Room;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private ReservationStatus status;
    @Column(length = 64)
    private String eventName;
    @Column(length = 280)
    private String eventDescription;
    @Basic
    private LocalDate date;
    @Basic
    private LocalTime startTime;
    @Basic
    private LocalTime endTime;

    public enum ReservationStatus {
        ACTIVE,
        EXPIRED,
        CANCELED
    }

}
