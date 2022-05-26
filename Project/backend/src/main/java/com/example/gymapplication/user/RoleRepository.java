package com.example.gymapplication.user;

import com.example.gymapplication.user.model.ERole;
import com.example.gymapplication.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);

}
