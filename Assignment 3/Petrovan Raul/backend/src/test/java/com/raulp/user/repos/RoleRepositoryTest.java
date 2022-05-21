package com.raulp.user.repos;

import com.raulp.user.model.ERole;
import com.raulp.user.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
    }

    @Test
    void findByName() {
        roleRepository.save(Role.builder().name(ERole.STUDENT).build());
        assertTrue(roleRepository.findByName(ERole.STUDENT).isPresent());
        assertFalse(roleRepository.findByName(ERole.ADMIN).isPresent());
    }
}