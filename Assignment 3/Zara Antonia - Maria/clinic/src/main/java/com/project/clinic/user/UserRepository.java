package com.project.clinic.user;


import com.project.clinic.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET points = points + :price WHERE id = :id", nativeQuery = true)
    void buy(Long id, int price);
}
