package com.project.clinic.treatment;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TreatmentRepositoryTest {

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
    public void beforeEach(){
        appointmentRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        skinColorRepository.deleteAll();
        treatmentRepository.deleteAll();
    }

    @Test
    void existsByTitle(){
        Treatment treatment = TestCreationFactory.newTreatment();
        treatmentRepository.save(treatment);

        Assertions.assertEquals(true, treatmentRepository.existsByTitle(treatment.getTitle()));
    }
}
