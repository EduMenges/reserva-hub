package com.reservahub.backend.app.room.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomFilterDto {
    private String roomNumber;
    private String buildingNumber;
    private String roomType;
    private Short capacity;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private List<String> resources;
}
