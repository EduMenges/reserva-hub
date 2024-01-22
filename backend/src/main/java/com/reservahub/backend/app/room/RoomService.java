package com.reservahub.backend.app.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.reservahub.backend.app.room.dto.RoomFilterDto;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> filterRooms(RoomFilterDto filter) {
        Specification<Room> spec = RoomFilter.dynamicRoomFilter(filter);
        return roomRepository.findAll(spec);
    }
}
