package com.reservahub.backend.app.reservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.reservation.ReservationRepository;
import com.reservahub.backend.app.reservationRequest.ReservationRequest.ReservationRequestStatus;
import com.reservahub.backend.app.room.RoomRepository;
import com.reservahub.backend.app.user.UserDetails;

@Service
public class ReservationRequestService {
    
    @Autowired
    private ReservationRequestRepository reservationRequestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public void emitRequest(
            UserDetails user,
            ReservationRequestDTO dto) 
            throws ResponseStatusException {

        if (roomDoesNotExist(dto.getRoomId())) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "The informed room does not exist."
            );
        }

        if (dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, 
                    "Invalid event duration."
            );
        }

        if (conflictsWithActiveReservations(dto.getRoomId(),
                                            dto.getDate(),
                                            dto.getStartTime(),
                                            dto.getEndTime())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, 
                    "The room was already reserved."
            );
        }

        ReservationRequest request = buildRequest(user, dto);

        reservationRequestRepository.save(request);
    }

    private boolean roomDoesNotExist(Long roomId) {
        System.out.println(roomId);

        if (roomRepository.findById(roomId).isEmpty()) {
            return true;
        }

        return false;
    }

    private boolean conflictsWithActiveReservations(Long roomId,
            LocalDate date, LocalTime startTime, LocalTime endTime) {
        ArrayList<Reservation> candidateConflicts =
                reservationRepository.findActiveReservationsByRoomIdAndDate(
                        roomId, date
                );

        for (Reservation reservation : candidateConflicts) {
            boolean happensBefore = (
                    reservation.getStartTime().isBefore(startTime) &&
                    reservation.getEndTime().isBefore(startTime)
            );

            boolean happensAfter = (
                    reservation.getStartTime().isAfter(endTime) &&
                    reservation.getEndTime().isAfter(endTime)
            );

            if (!(happensBefore || happensAfter)) {
                return true;
            }
        }

        return false;
    }

    private ReservationRequest buildRequest(
            UserDetails user,
            ReservationRequestDTO dto) {
        ReservationRequest request = new ReservationRequest();

        request.setCardNumber(user.getId());
        request.setRoomId(dto.getRoomId());
        request.setStatus(ReservationRequestStatus.AWAITING_APPROVAL);
        request.setEventName(dto.getEventName());
        request.setEventDescription(dto.getEventDescription());
        request.setDate(dto.getDate());
        request.setStartTime(dto.getStartTime());
        request.setEndTime(dto.getEndTime());

        return request;
    }

}
