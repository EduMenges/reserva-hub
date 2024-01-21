package com.reservahub.backend.app.history.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryEntryDTO {

    private UserInfo userInfo = new UserInfo();
    private RoomInfo roomInfo = new RoomInfo();
    private EntryMapping entryMapping = new EntryMapping();
    private String eventName;
    private EntryStatus status;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @Data
    public class RoomInfo {
        private String roomNumber;
        private String buildingNumber;
    }

    @Data
    public class EntryMapping {
        private EntityType type;
        private Long entityId;
    }

    @Data
    public class UserInfo {
        private String username;
        private Long userId;
        private String role;
    }

    public enum EntityType {
        RESERVATION,
        RESERVATION_REQUEST,
        EDITION_REQUEST
    }

    public enum EntryStatus {
        ACTIVE,
        EXPIRED,
        CANCELED,
        AWAITING_APPROVAL,
        APPROVED,
        DENIED
    }
}
