package com.policeSection.user;

import com.policeSection.user.model.ERole;
import com.policeSection.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
