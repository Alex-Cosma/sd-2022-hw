package com.example.leaguecomp.user;

import com.example.leaguecomp.user.model.ERole;
import com.example.leaguecomp.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRole(ERole role);
}
