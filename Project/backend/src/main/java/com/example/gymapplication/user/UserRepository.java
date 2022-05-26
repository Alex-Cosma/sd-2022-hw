package com.example.gymapplication.user;

import com.example.gymapplication.user.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    @Modifying
    @Transactional
    void deleteAll();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
