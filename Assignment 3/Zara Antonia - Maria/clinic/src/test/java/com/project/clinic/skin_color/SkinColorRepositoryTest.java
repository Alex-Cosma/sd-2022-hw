package com.project.clinic.skin_color;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.treatment.TreatmentRepository;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SkinColorRepositoryTest {

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
        SkinColor skinColor = TestCreationFactory.newSkinColor();
        skinColorRepository.save(skinColor);

        SkinColor found = skinColorRepository.findByName(skinColor.getName());
        Assertions.assertEquals(skinColor.getName().name(), found.getName().name());

    }
}
