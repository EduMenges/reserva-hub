package com.reservahub.backend.app.room;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String buildingNumber;
    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private RoomEnum type;
    private Short capacity;

    @ElementCollection(targetClass = ResourceEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "room_resources")
    @Column(name = "room_type")
    private Set<ResourceEnum> resources;

    public enum RoomEnum {
        AUDITORIUM,
        CLASSROOM,
        LAB,
        MEETING_ROOM
    }

    public enum ResourceEnum {
        AC,
        PROJECTOR,
        PC
    }

}
