package com.app.bookingapp.data.sql.repo;

import com.app.bookingapp.data.sql.entity.Role;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole role);
}
