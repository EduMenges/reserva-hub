package com.reservahub.backend.app.editionRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO.EntityType;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO.EntryMapping;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO.EntryStatus;

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

    public UserHistoryEntryDTO convertToUserHistoryEntry() {
        UserHistoryEntryDTO historyEntry = new UserHistoryEntryDTO();

        EntryMapping mapping = historyEntry.getEntryMapping();
        mapping.setType(EntityType.EDITION_REQUEST);
        mapping.setEntityId(id);

        historyEntry.setEntryMapping(mapping);

        historyEntry.setEventName(eventName);

        switch (status) {
            case AWAITING_APPROVAL:
            historyEntry.setStatus(EntryStatus.AWAITING_APPROVAL);
            break;
            case APPROVED:
            historyEntry.setStatus(EntryStatus.APPROVED);
            break;
            case DENIED:
            historyEntry.setStatus(EntryStatus.DENIED);
            break;
            case CANCELED:
            historyEntry.setStatus(EntryStatus.CANCELED);
            break;
        }

        historyEntry.setRoomNumber(room.getRoomNumber());
        historyEntry.setBuildingNumber(room.getBuildingNumber());
        historyEntry.setDate(date);
        historyEntry.setStartTime(startTime);
        historyEntry.setEndTime(endTime);

        return historyEntry;
    }
    
}
