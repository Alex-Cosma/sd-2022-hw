package com.app.bookingapp.repo;

import com.app.bookingapp.data.sql.entity.Role;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import com.app.bookingapp.data.sql.repo.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static com.app.bookingapp.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    void setUp(){
        roleRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        Role savedRole = saveRole();

        assertNotNull(savedRole);

        assertThrows(DataIntegrityViolationException.class, () -> {
            roleRepository.save(Role.builder()
                    .build());
        });
    }

    @Test
    public void testFindByName(){
        Role role = saveRole();

        Optional<Role> result = roleRepository.findByName(role.getName());

        assertTrue(result.isPresent());
        result.ifPresent(r -> assertEquals(r.getId(), role.getId()));
    }

    @Test
    public void testDeleteById(){
        Role savedRole = saveRole();

        roleRepository.deleteById(savedRole.getId());

        Optional<Role> result = roleRepository.findById(savedRole.getId());
        assertTrue(result.isEmpty());
    }

    private Role saveRole(){
        return roleRepository.save(Role.builder()
                .name(ERole.CLIENT)
                .description(randomString())
                .build());
    }
}
