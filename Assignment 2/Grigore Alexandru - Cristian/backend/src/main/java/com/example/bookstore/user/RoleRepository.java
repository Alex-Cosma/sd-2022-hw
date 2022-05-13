package com.example.bookstore.user;

import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> , JpaSpecificationExecutor<Role> {
    Optional<Role> findByRole(ERole role);
}
