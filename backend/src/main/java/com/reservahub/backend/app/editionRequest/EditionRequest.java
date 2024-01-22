package com.reservahub.backend.app.editionRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.user.User;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "edition_requests")
public class EditionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;
    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private EditionRequestStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
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

    public enum EditionRequestStatus {
        AWAITING_APPROVAL,
        APPROVED,
        DENIED,
        CANCELED
    }
}
