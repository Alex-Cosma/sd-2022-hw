package com.project.clinic.user;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SkinColorRepository skinColorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void beforeEach() {
        appointmentRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        skinColorRepository.deleteAll();
    }

    @Test
    void findByUsername(){
        User user = TestCreationFactory.newUser();

        skinColorRepository.save(user.getSkinColor());
        roleRepository.saveAll(user.getRoles());
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername(user.getUsername());

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(user.getUsername(), found.get().getUsername());
    }

    @Test
    void existsByUsername(){
        User user = TestCreationFactory.newUser();

        skinColorRepository.save(user.getSkinColor());
        roleRepository.saveAll(user.getRoles());
        userRepository.save(user);

        Boolean existence = userRepository.existsByUsername(user.getUsername());

        Assertions.assertTrue(existence);
    }

    @Test
    void buy(){
        User user = TestCreationFactory.newUser();

        skinColorRepository.save(user.getSkinColor());
        roleRepository.saveAll(user.getRoles());

        User saved = userRepository.save(user);
        userRepository.buy(saved.getId(), 100);

        User updated = userRepository.findById(saved.getId()).get();

        Assertions.assertEquals(100, updated.getPoints());

    }

}
