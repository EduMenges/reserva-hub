package com.reservahub.backend.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.EntityType;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.EntryMapping;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.EntryStatus;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.RoomInfo;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.UserInfo;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.user.User;

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
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
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

    public UserHistoryEntryDTO convertToUserHistoryEntry() {
        UserHistoryEntryDTO historyEntry = new UserHistoryEntryDTO();
        
        UserInfo userInfo = historyEntry.getUserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setRole(user.getRole().name());
        userInfo.setUsername(user.getUsername());

        EntryMapping entryMapping = historyEntry.getEntryMapping();
        entryMapping.setType(EntityType.RESERVATION);
        entryMapping.setEntityId(id);

        RoomInfo roomInfo = historyEntry.getRoomInfo();
        roomInfo.setBuildingNumber(room.getBuildingNumber());
        roomInfo.setRoomNumber(room.getRoomNumber());

        
        historyEntry.setUserInfo(userInfo);
        historyEntry.setEntryMapping(entryMapping);
        historyEntry.setEventName(eventName);

        switch (status) {
            case ACTIVE:
            historyEntry.setStatus(EntryStatus.ACTIVE);
            break;
            case EXPIRED:
            historyEntry.setStatus(EntryStatus.EXPIRED);
            break;
            case CANCELED:
            historyEntry.setStatus(EntryStatus.CANCELED);
            break;
        }

        historyEntry.setDate(date);
        historyEntry.setStartTime(startTime);
        historyEntry.setEndTime(endTime);

        return historyEntry;
    }
}
