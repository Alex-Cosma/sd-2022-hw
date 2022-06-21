package com.rdaniel.sd.project.user;

import com.rdaniel.sd.project.user.model.Role;
import com.rdaniel.sd.project.user.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleType role);
}

