package com.lab4.demo.user;

import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByUsernameLikeOrEmailLike(String username, String email, Pageable pageable);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findAllByRolesName(ERole client);
}
