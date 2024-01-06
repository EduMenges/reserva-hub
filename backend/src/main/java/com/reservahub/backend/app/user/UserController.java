package com.reservahub.backend.app.user;

import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.auth.AuthRequest;
import com.reservahub.backend.app.auth.JwtService;
import com.reservahub.backend.app.user.dto.LoginResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtService.generateToken(userDetails.getUsername());
        final Date expirationDate = jwtService.extractExpiration(token);
        final String role = userDetails.getAuthorityName();

        LoginResponseDto responseDto = new LoginResponseDto(token, "Bearer", expirationDate, role);
        return ResponseEntity.ok(responseDto);
    }

}