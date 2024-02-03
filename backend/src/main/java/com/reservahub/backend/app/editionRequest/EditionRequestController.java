package com.reservahub.backend.app.editionRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.editionRequest.dto.ChangeStatusEditionRequestDto;
import com.reservahub.backend.app.editionRequest.dto.EditionRequestDto;
import com.reservahub.backend.app.editionRequest.dto.EditionRequestResponseDto;
import com.reservahub.backend.app.user.UserDetails;

@RestController
@RequestMapping("/edit")
public class EditionRequestController {
    @Autowired
    EditionRequestService editionRequestService;

    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @PostMapping("/request")
    public ResponseEntity<EditionRequestResponseDto> saveEditionRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody EditionRequestDto request) {
        EditionRequest emitedRequest = editionRequestService.saveEditionRequest(userDetails, request);

        return ResponseEntity.ok(new EditionRequestResponseDto(emitedRequest.getId(), emitedRequest.getEventName(), emitedRequest.getStatus().name()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/approve")
    public ResponseEntity<EditionRequestResponseDto> approveEditionRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangeStatusEditionRequestDto changeStatusEditionRequest) {
        EditionRequest approvedRequest = editionRequestService.approveEditionRequest(userDetails, changeStatusEditionRequest.getEditionRequestId());

        return ResponseEntity.ok(new EditionRequestResponseDto(approvedRequest.getId(), approvedRequest.getEventName(), approvedRequest.getStatus().name()));
    }

    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER') or hasAuthority('ADMIN')")
    @PostMapping("/cancel")
    public ResponseEntity<EditionRequestResponseDto> cancelEditionRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangeStatusEditionRequestDto changeStatusEditionRequest) {
        EditionRequest canceledRequest = editionRequestService.cancelEditionRequest(userDetails, changeStatusEditionRequest.getEditionRequestId());

        return ResponseEntity.ok(new EditionRequestResponseDto(canceledRequest.getId(), canceledRequest.getEventName(), canceledRequest.getStatus().name()));
    }
}
