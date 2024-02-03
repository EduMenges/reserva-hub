package com.reservahub.backend.app.editionRequest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditionRequestResponseDto {
    private Long editionRequestId;
    private String eventName;
    private String status;
}
