package com.reservahub.backend.app.userHistory;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.user.User;
import com.reservahub.backend.app.user.UserDetails;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO;

@RestController
@RequestMapping("/user-history")
public class UserHistoryController {

    @Autowired
    private UserHistoryService historyService;

    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @GetMapping("/get")
    public ResponseEntity<ArrayList<UserHistoryEntryDTO>> getHistory(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userDetails.getId();

        if (User.RoleEnum.TEACHER.name().equals(userDetails.getAuthorityName())) {
            return ResponseEntity.ok(historyService.getTeacherHistory(userId));
        } else {
            return ResponseEntity.ok(historyService.getStudentHistory(userId));
        }
    }

}
