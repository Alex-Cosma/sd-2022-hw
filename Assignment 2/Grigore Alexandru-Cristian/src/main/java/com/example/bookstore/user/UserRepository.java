package com.example.bookstore.user;

import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByRolesEquals(ERole role);


}
