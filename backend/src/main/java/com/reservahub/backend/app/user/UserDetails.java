package com.reservahub.backend.app.user;

import java.util.List;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Data;

@Data
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{
    Long id;
    private String username; 
    private String password; 
    private List<GrantedAuthority> authorities; 
  
    public UserDetails(User user) { 
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword(); 
        authorities = Collections.singletonList(
            new SimpleGrantedAuthority(user.getRole().name())
        );
    }

    public String getAuthorityName() { 
        return authorities.get(0).getAuthority(); 
    } 
  
    @Override
    public boolean isAccountNonExpired() { 
        return true; 
    } 
  
    @Override
    public boolean isAccountNonLocked() { 
        return true; 
    } 
  
    @Override
    public boolean isCredentialsNonExpired() { 
        return true; 
    } 
  
    @Override
    public boolean isEnabled() { 
        return true; 
    } 
    
}