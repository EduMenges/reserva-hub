package com.reservahub.backend.app.reservationRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.reservahub.backend.app.reservation.Reservation;
import com.reservahub.backend.app.reservation.ReservationRepository;
import com.reservahub.backend.app.room.Room;
import com.reservahub.backend.app.room.RoomRepository;
import com.reservahub.backend.app.user.UserDetails;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationRequestServiceTest {
    
    @Autowired
    private ReservationRequestService reservationRequestService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private ReservationRequestDTO reservationRequestDTO;

    @Mock
    private Room room;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private ReservationRequestRepository reservationRequestRepository;

    @Before
    public void setup() {
        when(roomRepository
                .findById(anyLong()))
                .thenReturn(Optional.of(room));

        when(reservationRequestDTO
                .getStartTime())
                .thenReturn(LocalTime.of(20, 30, 0));

        when(reservationRequestDTO
                .getEndTime())
                .thenReturn(LocalTime.of(21, 0, 0));

        when(reservationRepository
                .findActiveReservationsByRoomIdAndDate(anyLong(), any()))
                .thenReturn(new ArrayList<>());
    }

    @Test
    public void testRoomIsNotRegistered() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, 
                () -> reservationRequestService.emitRequest(
                        userDetails,
                        reservationRequestDTO
                )
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void testEventDurationIsInvalid() {
        when(reservationRequestDTO
                .getStartTime())
                .thenReturn(LocalTime.of(21, 0, 0));

        when(reservationRequestDTO
                .getEndTime())
                .thenReturn(LocalTime.of(20, 0, 0));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, 
                () -> reservationRequestService.emitRequest(
                        userDetails,
                        reservationRequestDTO
                )
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testConflictThrowsException() {
        ArrayList<Reservation> reservations = new ArrayList<>();

        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.of(20, 0, 0));
        reservation.setEndTime(LocalTime.of(21, 0, 0));

        reservations.add(reservation);

        when(reservationRepository
                .findActiveReservationsByRoomIdAndDate(anyLong(), any()))
                .thenReturn(reservations);

        when(reservationRequestDTO
                .getStartTime())
                .thenReturn(LocalTime.of(20, 30, 0));

        when(reservationRequestDTO
                .getEndTime())
                .thenReturn(LocalTime.of(21, 0, 0));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class, 
                () -> reservationRequestService.emitRequest(
                        userDetails,
                        reservationRequestDTO
                )
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test
    public void testEmittionEndsIfOk() {
        reservationRequestService.emitRequest(userDetails,
                                              reservationRequestDTO);

        verify(reservationRequestRepository).save(any());
    }

}
