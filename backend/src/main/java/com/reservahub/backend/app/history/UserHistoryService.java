package com.reservahub.backend.app.history;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservahub.backend.app.editionRequest.EditionRequestRepository;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO;
import com.reservahub.backend.app.reservation.ReservationRepository;

@Service
public class UserHistoryService {

        @Autowired
        private EditionRequestRepository detailedEditionRequestRepository;

        @Autowired
        private ReservationRepository detailedReservationRepository;

        public ArrayList<UserHistoryEntryDTO> getStudentHistory(Long userId) {
                Stream<UserHistoryEntryDTO> editionRequestStream = detailedEditionRequestRepository
                                .findEditionsWaitingApprovalByUserIdOrdered(userId)
                                .stream()
                                .map(UserHistoryMapper::toDto);

                Stream<UserHistoryEntryDTO> reservationStream = detailedReservationRepository
                                .findReservationsByUserIdOrdered(userId)
                                .stream()
                                .map(UserHistoryMapper::toDto);

                return Stream.of(editionRequestStream, reservationStream)
                                .flatMap(Function.identity())
                                .collect(Collectors.toCollection(ArrayList::new));
        }

        public ArrayList<UserHistoryEntryDTO> getTeacherHistory(Long userId) {
                return detailedReservationRepository
                                .findReservationsByUserIdOrdered(userId)
                                .stream()
                                .map(UserHistoryMapper::toDto)
                                .collect(Collectors.toCollection(ArrayList::new));
        }

        public ArrayList<UserHistoryEntryDTO> getGlobalHistory() {
                Stream<UserHistoryEntryDTO> editionRequestStream = detailedEditionRequestRepository
                                .findAllEditions()
                                .stream()
                                .map(UserHistoryMapper::toDto);

                Stream<UserHistoryEntryDTO> reservationStream = detailedReservationRepository
                                .findAllReservations()
                                .stream()
                                .map(UserHistoryMapper::toDto);

                return Stream.of(editionRequestStream, reservationStream)
                                .flatMap(Function.identity())
                                .collect(Collectors.toCollection(ArrayList::new));
        }
}