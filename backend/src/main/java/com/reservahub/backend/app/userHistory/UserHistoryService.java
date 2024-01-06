package com.reservahub.backend.app.userHistory;

import java.util.ArrayList;

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
        ArrayList<EditionRequest> orderedEditionRequests =
                detailedEditionRequestRepository
                .findEditionsWaitingApprovalByUserIdOrdered(userId);
        ArrayList<ReservationRequest> orderedReservationRequests =
                detailedReservationRequestRepository
                .findRequestsWaitingApprovalByUserIdOrdered(userId);
        ArrayList<Reservation> orderedReservations =
                detailedReservationRepository
                .findReservationsByUserIdOrdered(userId);

        ArrayList<UserHistoryEntryDTO> historyEntries = new ArrayList<>();        
        
        for (EditionRequest editionRequest : orderedEditionRequests) {
            historyEntries.add(editionRequest.convertToUserHistoryEntry());
        }

        for (ReservationRequest reservationRequest : orderedReservationRequests) {
            historyEntries.add(reservationRequest.convertToUserHistoryEntry());
        }

        for (Reservation reservation : orderedReservations) {
            historyEntries.add(reservation.convertToUserHistoryEntry());
        }
        
        return historyEntries;
    }

    public ArrayList<UserHistoryEntryDTO> getTeacherHistory(Long userId) {
        ArrayList<Reservation> orderedReservations =
                detailedReservationRepository
                .findReservationsByUserIdOrdered(userId);

        ArrayList<UserHistoryEntryDTO> historyEntries = new ArrayList<>();        

        for (Reservation reservation : orderedReservations) {
            historyEntries.add(reservation.convertToUserHistoryEntry());
        }
        
        return historyEntries;
    }

}