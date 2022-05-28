package com.example.backend.user;

import com.example.backend.user.model.ERole;
import com.example.backend.user.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
