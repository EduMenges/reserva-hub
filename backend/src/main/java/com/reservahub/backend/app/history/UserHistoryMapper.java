package com.reservahub.backend.app.history;

import java.time.LocalDate;
import java.time.LocalTime;
import com.reservahub.backend.app.editionRequest.EditionRequest;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.EntityType;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.EntryStatus;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.RoomInfo;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.UserInfo;
import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.user.User;

public class UserHistoryMapper {

    public static UserHistoryEntryDTO toDto(EditionRequest editionRequest) {
        UserHistoryEntryDTO dto = createBasicDto(editionRequest.getUser(), editionRequest.getRoom());
        setEventDetails(dto, editionRequest.getEventName(), editionRequest.getEventDescription(),
                editionRequest.getDate(), editionRequest.getStartTime(),
                editionRequest.getEndTime());
        dto.getEntryMapping().setType(EntityType.EDITION_REQUEST);
        dto.getEntryMapping().setEntityId(editionRequest.getId());
        updateEntryStatus(dto, editionRequest.getStatus());
        return dto;
    }

    public static UserHistoryEntryDTO toDto(Reservation reservation) {
        UserHistoryEntryDTO dto = createBasicDto(reservation.getUser(), reservation.getRoom());
        setEventDetails(dto, reservation.getEventName(), reservation.getEventDescription(), reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime());
        dto.getEntryMapping().setType(determineReservationType(reservation.getStatus()));
        dto.getEntryMapping().setEntityId(reservation.getId());
        updateEntryStatus(dto, reservation.getStatus());
        return dto;
    }

    private static UserHistoryEntryDTO createBasicDto(User user, Room room) {
        UserHistoryEntryDTO dto = new UserHistoryEntryDTO();
        dto.setUserInfo(new UserInfo(user.getUsername(), user.getId(), user.getRole().name()));
        dto.setRoomInfo(new RoomInfo(room.getId(), room.getRoomNumber(), room.getBuildingNumber()));
        return dto;
    }

    private static void setEventDetails(UserHistoryEntryDTO dto, String eventName, String eventDescription,
            LocalDate date, LocalTime startTime,
            LocalTime endTime) {
        dto.setEventName(eventName);
        dto.setDate(date);
        dto.setEventDescription(eventDescription);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
    }

    private static EntityType determineReservationType(Reservation.ReservationStatus status) {
        return status == Reservation.ReservationStatus.AWAITING_APPROVAL
                || status == Reservation.ReservationStatus.DENIED
                        ? EntityType.RESERVATION_REQUEST
                        : EntityType.RESERVATION;
    }

    private static void updateEntryStatus(UserHistoryEntryDTO dto, Enum<?> status) {
        if (status != null) {
            dto.setStatus(EntryStatus.valueOf(status.name()));
        }
    }
}