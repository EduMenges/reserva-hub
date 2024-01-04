package com.reservahub.backend.app.room;

import jakarta.persistence.Column;
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
    private RoomType type;
    private Short capacity;

    public enum RoomType {
        CLASSROOM,
        LAB
    }

}
