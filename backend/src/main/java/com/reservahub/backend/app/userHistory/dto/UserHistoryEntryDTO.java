package com.reservahub.backend.app.userHistory.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryEntryDTO {
    private EntryMapping entryMapping = new EntryMapping();
    private String eventName;
    private EntryStatus status;
    private String roomNumber;
    private String buildingNumber;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @Data
    public class EntryMapping {
        private EntityType type;
        private Long entityId;
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
