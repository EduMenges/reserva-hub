package com.reservahub.backend.app.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.room.dto.RoomFilterDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/filter")
    public ResponseEntity<List<Room>> getRooms(@RequestBody RoomFilterDto filter) {
        List<Room> rooms = roomService.filterRooms(filter);
        return ResponseEntity.ok(rooms);
    }
    
    
}
