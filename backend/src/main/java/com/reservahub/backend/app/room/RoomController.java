package com.reservahub.backend.app.room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.room.dto.RoomFilterDto;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/filter")
    public ResponseEntity<List<Room>> getRooms(@RequestParam(required = false) String roomNumber, @RequestParam(required = false) String buildingNumber, @RequestParam(required = false) String roomType, @RequestParam(required = false) Short capacity, @RequestParam(required = false) LocalTime startTime, @RequestParam(required = false) LocalTime endTime, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) List<String> resources) {
        RoomFilterDto filter = new RoomFilterDto(roomNumber, buildingNumber, roomType, capacity, startTime, endTime, date, resources);
        List<Room> rooms = roomService.filterRooms(filter);
        return ResponseEntity.ok(rooms);
    }
    
    
}
