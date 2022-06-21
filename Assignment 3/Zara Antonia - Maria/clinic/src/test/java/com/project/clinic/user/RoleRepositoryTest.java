package com.project.clinic.user;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.treatment.TreatmentRepository;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.user.UserRepository;
import com.project.clinic.user.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RoleRepositoryTest {
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SkinColorRepository skinColorRepository;

    @BeforeEach
    void setUp(){
        appointmentRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        skinColorRepository.deleteAll();
        treatmentRepository.deleteAll();
    }

    @Test
    void findByName(){
        Role role = TestCreationFactory.newRole();
        roleRepository.save(role);

        Optional<Role> found = roleRepository.findByName(role.getName());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(role.getName().name(), found.get().getName().name());

    }
}
