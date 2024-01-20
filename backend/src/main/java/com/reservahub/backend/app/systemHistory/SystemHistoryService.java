package com.reservahub.backend.app.systemHistory;

import java.util.ArrayList;
//import java.util.function.Function;
import java.util.stream.Collectors;
//import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.reservahub.backend.app.editionRequest.EditionRequest;
//import com.reservahub.backend.app.editionRequest.EditionRequestRepository;
import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.reservation.ReservationRepository;
//import com.reservahub.backend.app.reservationRequest.ReservationRequest;
//import com.reservahub.backend.app.reservationRequest.ReservationRequestRepository;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO;

@Service
public class SystemHistoryService {

    @Autowired
    private ReservationRepository detailedReservationRepository;

    public ArrayList<UserHistoryEntryDTO> getGlobalHistory() {
        return detailedReservationRepository.findAllReservations()
                .stream()
                .map(Reservation::convertToUserHistoryEntry)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}