package com.reservahub.backend.app.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long Id);
    Optional<User> findByRegistration(String registration);

}
