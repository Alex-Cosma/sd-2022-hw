package com.project.clinic.skin_color;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.treatment.TreatmentRepository;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SkinColorServiceIntegrationTest {

    @Autowired
    private SkinColorService skinColorService;

    @Autowired
    private SkinColorRepository skinColorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @BeforeEach
    void setUp(){
        appointmentRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        skinColorRepository.deleteAll();
        treatmentRepository.deleteAll();
    }

    @Test
    void findAll(){
        List<SkinColor> skinColors = TestCreationFactory.listOf(SkinColor.class);
        skinColorRepository.saveAll(skinColors);

        List<SkinColor> all = skinColorService.findAll();

        Assertions.assertEquals(skinColors.size(), all.size());
    }

    @Test
    void findById(){
        SkinColor skinColor = TestCreationFactory.newSkinColor();
        SkinColor saved = skinColorRepository.save(skinColor);

        SkinColor found = skinColorService.findById(saved.getId());

        Assertions.assertEquals(skinColor.getName().name(), found.getName().name());
    }

    @Test
    void findByName(){
        SkinColor skinColor = TestCreationFactory.newSkinColor();
        SkinColor saved = skinColorRepository.save(skinColor);

        SkinColor found = skinColorService.findByName(saved.getName());

        Assertions.assertEquals(skinColor.getId(), found.getId());
    }
}
