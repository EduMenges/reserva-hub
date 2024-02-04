package com.reservahub.backend.app.history;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO;
import com.reservahub.backend.app.history.dto.UserHistoryEntryDTO.EntityType;
import com.reservahub.backend.app.user.User;
import com.reservahub.backend.app.user.UserDetails;

@RestController
@RequestMapping("/history")
public class UserHistoryController {

    @Autowired
    private UserHistoryService historyService;

    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER') or hasAuthority('ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<ArrayList<UserHistoryEntryDTO>> getHistory(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userDetails.getId();

        if (User.RoleEnum.TEACHER.name().equals(userDetails.getAuthorityName())) {
            return ResponseEntity.ok(historyService.getTeacherHistory(userId));
        } else if (User.RoleEnum.STUDENT.name().equals(userDetails.getAuthorityName())) {
            return ResponseEntity.ok(historyService.getStudentHistory(userId));
        } else {
            return ResponseEntity.ok(historyService.getGlobalHistory());
        }
    }

    //@PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER') or hasAuthority('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<UserHistoryEntryDTO> searchHistory(@RequestParam(required = true) Long entryId, @RequestParam(required = true) EntityType type) {
        return ResponseEntity.ok(historyService.getSingleEntryHistory(entryId, type));
    }

}
