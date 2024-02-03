package com.reservahub.backend.app.editionRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.reservahub.backend.app.editionRequest.EditionRequest.EditionRequestStatus;
import com.reservahub.backend.app.editionRequest.dto.EditionRequestDto;
import com.reservahub.backend.app.exception.InvalidEditRequestException;
import com.reservahub.backend.app.exception.RoomAlreadyReservedException;
import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.reservation.ReservationRepository;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.room.RoomRepository;
import com.reservahub.backend.app.user.User;
import com.reservahub.backend.app.user.UserDetails;
import com.reservahub.backend.app.user.UserRepository;
import com.reservahub.backend.app.utils.ValidationUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EditionRequestService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EditionRequestRepository editionRequestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public EditionRequest cancelEditionRequest(UserDetails userDetails, Long editionRequestId) {
        EditionRequest editionRequest = findEditionRequest(editionRequestId);
        if (!editionRequest.getUser().getId().equals(userDetails.getId())
                && !userDetails.getAuthorityName().equals(User.RoleEnum.ADMIN.name())) {
            throw new AccessDeniedException("Acess Denied");
        }
        if(userDetails.getAuthorityName().equals(User.RoleEnum.ADMIN.name())){
            editionRequest.setStatus(EditionRequestStatus.DENIED);
        }else{
            editionRequest.setStatus(EditionRequestStatus.CANCELED);
        }

        return editionRequestRepository.save(editionRequest);
    }

    @Transactional
    public EditionRequest approveEditionRequest(UserDetails userDetails, Long editionRequestId) {
        EditionRequest editionRequest = findEditionRequest(editionRequestId);
        if (!userDetails.getAuthorityName().equals(User.RoleEnum.ADMIN.name())) {
            throw new AccessDeniedException("Acess Denied");
        }
        validateApprovalPossibility(editionRequest);
        validateRoomAvailability(editionRequest.getRoom().getId(), editionRequest.getDate(),
                editionRequest.getStartTime(), editionRequest.getEndTime());
        editionRequest.setStatus(EditionRequestStatus.APPROVED);

        Reservation reservation = editionRequest.getReservation();
        updateRequest(reservation, editionRequest);

        return editionRequestRepository.save(editionRequest);
    }

    @Transactional
    public void updateRequest(Reservation reservation, EditionRequest editionRequest) {
        reservation.setRoom(editionRequest.getRoom());
        reservation.setEventDescription(editionRequest.getEventDescription());
        reservation.setEventName(editionRequest.getEventName());
        reservation.setDate(editionRequest.getDate());
        reservation.setStartTime(editionRequest.getStartTime());
        reservation.setEndTime(editionRequest.getEndTime());
        editionRequestRepository.save(editionRequest);
    }

    @Transactional
    public EditionRequest saveEditionRequest(UserDetails userDetails, EditionRequestDto dto) {
        Reservation reservation = findReservation(dto.getReservationId());
        validateReservation(reservation);
        Room room = findRoom(dto.getRoomId());
        User user = findUser(userDetails.getId());
        ValidationUtils.validateAuthencity(reservation.getUser().getId(), user.getId());
        ValidationUtils.validateEventDate(dto.getDate());
        ValidationUtils.validateEventDuration(dto.getStartTime(), dto.getEndTime());
        EditionRequestStatus status = userDetails.getAuthorityName() == User.RoleEnum.STUDENT.name()
                ? EditionRequestStatus.AWAITING_APPROVAL
                : EditionRequestStatus.APPROVED;
        validateRoomAvailability(dto.getRoomId(), dto.getDate(), dto.getStartTime(), dto.getEndTime());
        EditionRequest request = buildRequest(user, dto, reservation, room, status);
        if (status == EditionRequestStatus.APPROVED) {
            updateRequest(reservation, request);
        }
        return editionRequestRepository.save(request);
    }


    private EditionRequest findEditionRequest(Long editionRequestId) {
        return editionRequestRepository.findById(editionRequestId)
                .orElseThrow(() -> new EntityNotFoundException("Edition Request not found"));
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

    private void validateRoomAvailability(Long roomId, LocalDate date, LocalTime startTime,
            LocalTime endTime) {
        boolean hasConflict = reservationRepository.existsActiveReservationWithTimeConflict(roomId, date, startTime,
                endTime);
        if (hasConflict) {
            throw new RoomAlreadyReservedException();
        }
    }

    private void validateApprovalPossibility(EditionRequest editionRequest) {
        if (editionRequest.getStatus() != EditionRequestStatus.AWAITING_APPROVAL) {
            throw new InvalidEditRequestException("Edition Request is not awaiting approval.");
        }
    }

    private void validateReservation(Reservation reservation) {
        if (reservation.getStatus() != Reservation.ReservationStatus.AWAITING_APPROVAL && reservation.getStatus() != Reservation.ReservationStatus.ACTIVE){
            throw new InvalidEditRequestException("Reservation is not awaiting approval or active.");
        }
    }
    

    private EditionRequest buildRequest(User user, EditionRequestDto dto, Reservation reservation, Room room,
            EditionRequestStatus status) {
        EditionRequest request = new EditionRequest();
        request.setReservation(reservation);
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
