package com.reservahub.backend.app.reservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservahub.backend.app.exception.InvalidEventDurationException;
import com.reservahub.backend.app.exception.RoomAlreadyReservedException;
import com.reservahub.backend.app.reservation.ReservationRepository;
import com.reservahub.backend.app.reservationRequest.ReservationRequest.ReservationRequestStatus;
import com.reservahub.backend.app.reservationRequest.dto.ReservationRequestDto;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.room.RoomRepository;
import com.reservahub.backend.app.user.UserDetails;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    public ReservationRequest emitRequest(UserDetails user, ReservationRequestDto dto) {
        Room room = findRoom(dto.getRoomId());
        validateEventDuration(dto);
        validateRoomAvailability(dto, room);

        ReservationRequest request = buildRequest(user, dto, room);
        reservationRequestRepository.save(request);
        return request;
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }

    private void validateEventDuration(ReservationRequestDto dto) {
        if (dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new InvalidEventDurationException();
        }
    }

    private void validateRoomAvailability(ReservationRequestDto dto, Room room) {
        if (hasConflictsWithActiveReservations(dto.getRoomId(), dto.getDate(), dto.getStartTime(), dto.getEndTime())) {
            throw new RoomAlreadyReservedException();
        }
    }

    private boolean hasConflictsWithActiveReservations(Long roomId, LocalDate date, LocalTime startTime,
            LocalTime endTime) {
       return reservationRepository.existsActiveReservationWithTimeConflict(roomId, date, startTime, endTime);
    }

    private ReservationRequest buildRequest(UserDetails user, ReservationRequestDto dto, Room room) {
        ReservationRequest request = new ReservationRequest();
        request.setUserId(user.getId());
        request.setRoom(room);
        request.setStatus(ReservationRequestStatus.AWAITING_APPROVAL);
        request.setEventName(dto.getEventName());
        request.setEventDescription(dto.getEventDescription());
        request.setDate(dto.getDate());
        request.setStartTime(dto.getStartTime());
        request.setEndTime(dto.getEndTime());
        return request;
    }

}
