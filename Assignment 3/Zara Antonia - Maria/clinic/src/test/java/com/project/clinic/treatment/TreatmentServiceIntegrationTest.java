package com.project.clinic.treatment;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.AppointmentRepository;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TreatmentServiceIntegrationTest {

    @Autowired
    private TreatmentService treatmentService;

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
    void findById(){
        Treatment treatment = TestCreationFactory.newTreatment();
        Treatment saved = treatmentRepository.save(treatment);

        Treatment found = treatmentService.findById(saved.getId());
        Assertions.assertEquals(saved.getTitle(), found.getTitle());
    }

    @Test
    void create(){
        TreatmentDTO dto = TestCreationFactory.newTreatmentDTO();

        List<Treatment> all = treatmentRepository.findAll();
        Assertions.assertEquals(0, all.size());

        treatmentService.create(dto);

        all = treatmentRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }
}
