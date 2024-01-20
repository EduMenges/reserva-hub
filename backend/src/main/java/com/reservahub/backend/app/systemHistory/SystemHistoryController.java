package com.reservahub.backend.app.systemHistory;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.reservahub.backend.app.user.User;
//import com.reservahub.backend.app.user.UserDetails;
//import com.reservahub.backend.app.userHistory.UserHistoryService;
import com.reservahub.backend.app.userHistory.dto.UserHistoryEntryDTO;

@RestController
@RequestMapping("/admin-history")
public class SystemHistoryController {
    @Autowired
    private SystemHistoryService historyService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get")

    public ResponseEntity<ArrayList<UserHistoryEntryDTO>> getGlobalHistory(){
        return ResponseEntity.ok(historyService.getGlobalHistory());
    }


}
