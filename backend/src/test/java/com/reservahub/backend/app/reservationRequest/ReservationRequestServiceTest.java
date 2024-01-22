// package com.reservahub.backend.app.reservationRequest;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.util.Optional;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit4.SpringRunner;

// import com.reservahub.backend.app.exception.InvalidDateException;
// import com.reservahub.backend.app.exception.InvalidEventDurationException;
// import com.reservahub.backend.app.exception.RoomAlreadyReservedException;
// import com.reservahub.backend.app.reservation.ReservationRepository;
// import com.reservahub.backend.app.reservationRequest.dto.ReservationRequestDto;
// import com.reservahub.backend.app.room.Room;
// import com.reservahub.backend.app.room.RoomRepository;
// import com.reservahub.backend.app.user.UserDetails;

// import jakarta.persistence.EntityNotFoundException;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class ReservationRequestServiceTest {

//         @Autowired
//         private ReservationRequestService reservationRequestService;

//         @Mock
//         private UserDetails userDetails;

//         @Mock
//         private ReservationRequestDto reservationRequestDTO;

//         @Mock
//         private Room room;

//         @MockBean
//         private RoomRepository roomRepository;

//         @MockBean
//         private ReservationRepository reservationRepository;

//         @MockBean
//         private ReservationRequestRepository reservationRequestRepository;

//         @Before
//         public void setup() {
//                 when(roomRepository
//                                 .findById(anyLong()))
//                                 .thenReturn(Optional.of(room));

//                 when(reservationRequestDTO
//                                 .getStartTime())
//                                 .thenReturn(LocalTime.of(20, 30, 0));

//                 when(reservationRequestDTO
//                                 .getEndTime())
//                                 .thenReturn(LocalTime.of(21, 0, 0));

//                 when(reservationRepository
//                                 .existsActiveReservationWithTimeConflict(anyLong(), any(LocalDate.class),
//                                                 any(LocalTime.class), any(LocalTime.class)))
//                                 .thenReturn(false);
//         }

//         @Test
//         public void shouldThrowEntityNotFoundExceptionForNonExistentRoom() {
//                 when(roomRepository.findById(anyLong())).thenReturn(Optional.empty());

//                 EntityNotFoundException exception = assertThrows(
//                                 EntityNotFoundException.class,
//                                 () -> reservationRequestService.emitRequest(
//                                                 userDetails,
//                                                 reservationRequestDTO));

//                 assertEquals("Room not found", exception.getMessage());
//         }

//         @Test
//         public void shouldThrowInvalidEventDurationExceptionForEndTimeBeforeStartTime() {
//                 when(reservationRequestDTO
//                                 .getStartTime())
//                                 .thenReturn(LocalTime.of(21, 0, 0));

//                 when(reservationRequestDTO
//                                 .getEndTime())
//                                 .thenReturn(LocalTime.of(20, 0, 0));

//                 InvalidEventDurationException exception = assertThrows(
//                                 InvalidEventDurationException.class,
//                                 () -> reservationRequestService.emitRequest(
//                                                 userDetails,
//                                                 reservationRequestDTO));

//                 assertEquals("Invalid event duration.", exception.getMessage());
//         }

//         @Test
//         public void shouldThrowInvalidDateExceptionForAnteriorToTodayDate() {
//                 when(reservationRequestDTO.getDate()).thenReturn(LocalDate.now().minusDays(5));


//                 InvalidDateException exception = assertThrows(
//                                 InvalidDateException.class,
//                                 () -> reservationRequestService.emitRequest(
//                                                 userDetails,
//                                                 reservationRequestDTO));

//                 assertEquals("Invalid date.", exception.getMessage());
//         }


//         @Test
//         public void shouldThrowRoomAlreadyReservedExceptionForTimeConflict() {
//                 when(reservationRepository.existsActiveReservationWithTimeConflict(
//                                 anyLong(), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
//                                 .thenReturn(true);

//                 when(reservationRequestDTO.getRoomId()).thenReturn(1L);
//                 when(reservationRequestDTO.getDate()).thenReturn(LocalDate.now());
//                 when(reservationRequestDTO.getStartTime()).thenReturn(LocalTime.of(10, 0));
//                 when(reservationRequestDTO.getEndTime()).thenReturn(LocalTime.of(12, 0));

//                 assertThrows(RoomAlreadyReservedException.class, () -> {
//                         reservationRequestService.emitRequest(userDetails, reservationRequestDTO);
//                 });
//         }

//         @Test
//         public void shouldSaveReservationRequestWhenNoConflictsOrErrors() {
//                 reservationRequestService.emitRequest(userDetails,
//                                 reservationRequestDTO);

//                 verify(reservationRequestRepository).save(any());
//         }

// }
