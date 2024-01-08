package com.reservahub.backend.app.userHistory;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservahub.backend.app.editionRequest.EditionRequest;
import com.reservahub.backend.app.editionRequest.EditionRequestRepository;
import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.reservation.ReservationRepository;
import com.reservahub.backend.app.reservationRequest.ReservationRequest;
import com.reservahub.backend.app.reservationRequest.ReservationRequestRepository;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO;

@Service
public class UserHistoryService {

    @Autowired
    private EditionRequestRepository detailedEditionRequestRepository;

    @Autowired
    private ReservationRequestRepository detailedReservationRequestRepository;

    @Autowired
    private ReservationRepository detailedReservationRepository;

    public ArrayList<UserHistoryEntryDTO> getStudentHistory(Long userId) {
        Stream<UserHistoryEntryDTO> editionRequestStream = detailedEditionRequestRepository
                .findEditionsWaitingApprovalByUserIdOrdered(userId)
                .stream()
                .map(EditionRequest::convertToUserHistoryEntry);

        Stream<UserHistoryEntryDTO> reservationRequestStream = detailedReservationRequestRepository
                .findRequestsWaitingApprovalByUserIdOrdered(userId)
                .stream()
                .map(ReservationRequest::convertToUserHistoryEntry);

        Stream<UserHistoryEntryDTO> reservationStream = detailedReservationRepository
                .findReservationsByUserIdOrdered(userId)
                .stream()
                .map(Reservation::convertToUserHistoryEntry);

        return Stream.of(editionRequestStream, reservationRequestStream, reservationStream)
                .flatMap(Function.identity())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<UserHistoryEntryDTO> getTeacherHistory(Long userId) {
        return detailedReservationRepository
                .findReservationsByUserIdOrdered(userId)
                .stream()
                .map(Reservation::convertToUserHistoryEntry)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}