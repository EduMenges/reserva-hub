package com.reservahub.backend.app.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
    private String registration; 
    private String password;    
}
