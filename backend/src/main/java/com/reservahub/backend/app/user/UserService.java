package com.reservahub.backend.app.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User saveUser(User user){
        user.setPassword(encoder.encode(user.getPassword())); 
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String registration) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByRegistration(registration);

        return user.map(com.reservahub.backend.app.user.UserDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + registration)); 

    }
}
