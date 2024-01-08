package com.reservahub.backend.app.userHistory;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.user.UserDetails;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO;

@RestController
@RequestMapping("/user-history")
public class UserHistoryController {
    
    @Autowired
    private UserHistoryService historyService;

    @GetMapping("/student")
    public ArrayList<UserHistoryEntryDTO> getStudentHistory() {
        Authentication authentication = SecurityContextHolder
                                       .getContext()
                                       .getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Long userId = userDetails.getId();

        return historyService.getStudentHistory(userId);
    }

    @GetMapping("/teacher")
    public ArrayList<UserHistoryEntryDTO> getTeacherHistory() {
        Authentication authentication = SecurityContextHolder
                                       .getContext()
                                       .getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Long userId = userDetails.getId();

        return historyService.getTeacherHistory(userId);
    }

}
