package com.reservahub.backend.app.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
    public Reservation cancelReservation(UserDetails userDetails, Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        
        if (reservation.getUser().getId() != userDetails.getId()
                && !userDetails.getAuthorityName().equals(User.RoleEnum.ADMIN.name())) {
            throw new AccessDeniedException("Acess Denied");
        }

        if (userDetails.getAuthorityName().equals(User.RoleEnum.ADMIN.name())) {
            reservation.setStatus(ReservationStatus.DENIED);
        } else {
            reservation.setStatus(ReservationStatus.CANCELED);
        }
        
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation approveReservation(UserDetails userDetails, Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        if(!userDetails.getAuthorityName().equals(User.RoleEnum.ADMIN.name())){
            throw new AccessDeniedException("Acess Denied");
        }
        reservation.setStatus(ReservationStatus.ACTIVE);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation saveReservation(UserDetails userDetails, ReservationRequestDto dto) {
        Room room = findRoom(dto.getRoomId());
        User user = findUser(userDetails.getId());
        ValidationUtils.validateEventDate(dto.getDate());
        ValidationUtils.validateEventDuration(dto.getStartTime(), dto.getEndTime());
        ReservationStatus status = userDetails.getAuthorityName() == User.RoleEnum.STUDENT.name()
                ? ReservationStatus.AWAITING_APPROVAL
                : ReservationStatus.ACTIVE;
        if (userDetails.getAuthorityName().equals(User.RoleEnum.TEACHER.name())) {
            validateRoomAvailabilityForTeacher(dto.getRoomId(), dto.getDate(), dto.getStartTime(), dto.getEndTime());
            ArrayList<Reservation> conflictingReservations =
                    reservationRepository.findConflictingReservations(
                            dto.getRoomId(),
                            dto.getDate(), 
                            dto.getStartTime(), 
                            dto.getEndTime()
                    );
            releaseReservations(conflictingReservations);
        } else {
            validateRoomAvailability(dto.getRoomId(), dto.getDate(), dto.getStartTime(), dto.getEndTime());
        }
        Reservation request = buildRequest(user, dto, room, status);
        return reservationRepository.save(request);
    }

    private void releaseReservations(ArrayList<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            reservation.setStatus(ReservationStatus.CANCELED);
        }
        if (reservations != null) {
            reservationRepository.saveAll(reservations);
        }
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private void validateRoomAvailabilityForTeacher(Long roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        boolean hasConflict = 
                reservationRepository.existsActiveReservationFromATeacherWithTimeConflict(roomId, date, startTime, endTime);
        if (hasConflict) {
            throw new RoomAlreadyReservedException();
        }
    }

    private void validateRoomAvailability(Long roomId, LocalDate date, LocalTime startTime,
    LocalTime endTime) {
        boolean hasConflict = reservationRepository.existsActiveReservationWithTimeConflict(roomId, date, startTime, endTime);
        if (hasConflict) {
            throw new RoomAlreadyReservedException();
        }
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
