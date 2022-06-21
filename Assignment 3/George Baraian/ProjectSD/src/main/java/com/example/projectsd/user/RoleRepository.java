package com.example.projectsd.user;

import com.example.projectsd.user.model.ERole;
import com.example.projectsd.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole role);
}
