package com.app.bookingapp.data.sql.repo;

import com.app.bookingapp.data.sql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}
