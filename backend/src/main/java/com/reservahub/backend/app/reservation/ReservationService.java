package com.reservahub.backend.app.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import com.reservahub.backend.app.exception.RoomAlreadyReservedException;
import com.reservahub.backend.app.reservation.Reservation.ReservationStatus;
import com.reservahub.backend.app.reservation.dto.ReservationRequestDto;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.room.RoomRepository;
import com.reservahub.backend.app.user.User;
import com.reservahub.backend.app.user.UserDetails;
import com.reservahub.backend.app.user.UserRepository;
import com.reservahub.backend.app.utils.ValidationUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Reservation saveReservation(UserDetails userDetails, ReservationRequestDto dto) {
        Room room = findRoom(dto.getRoomId());
        User user = findUser(userDetails.getId());
        ValidationUtils.validateEventDate(dto.getDate());
        ValidationUtils.validateEventDuration(dto.getStartTime(), dto.getEndTime());
        ReservationStatus status = userDetails.getAuthorityName() == User.RoleEnum.STUDENT.name()
                ? ReservationStatus.AWAITING_APPROVAL
                : ReservationStatus.ACTIVE;
        validateRoomAvailability(dto, room);
        Reservation request = buildRequest(user, dto, room, status);
        return reservationRepository.save(request);
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
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

    private Reservation buildRequest(User user, ReservationRequestDto dto, Room room, ReservationStatus status) {
        Reservation request = new Reservation();
        request.setUser(user);
        request.setRoom(room);
        request.setStatus(status);
        request.setEventName(dto.getEventName());
        request.setEventDescription(dto.getEventDescription());
        request.setDate(dto.getDate());
        request.setStartTime(dto.getStartTime());
        request.setEndTime(dto.getEndTime());
        return request;
    }
}