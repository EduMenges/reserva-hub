package com.reservahub.backend.app.user;

import org.springframework.web.bind.annotation.RestController;

import com.reservahub.backend.app.auth.AuthRequest;
import com.reservahub.backend.app.auth.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication; 


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    // Para Teste
    @PostMapping("/new")
    public User addNewUser(@RequestBody User user) {        
        return userService.saveUser(user);
    }

    // Para Teste
    @GetMapping("/hello")
    public String helloUser() {
        return "Hello";
    }

    @PostMapping("/login") 
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getRegistration(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getRegistration()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 
    
    
}
