package com.project.clinic.treatment;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.treatment.model.TreatmentCategory;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TreatmentServiceTest {

    @InjectMocks
    private TreatmentService treatmentService;

    @Mock
    private TreatmentRepository treatmentRepository;

    @Mock
    private TreatmentMapper treatmentMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        treatmentService = new TreatmentService(treatmentRepository, treatmentMapper);
    }

    @Test
    void findById(){
        Treatment treatment = TestCreationFactory.newTreatment();

        when(treatmentRepository.findById(treatment.getId())).thenReturn(java.util.Optional.of(treatment));

        Treatment found = treatmentService.findById(treatment.getId());

        Assertions.assertEquals(treatment.getTitle(), found.getTitle());
    }

    @Test
    void create(){
        Treatment treatment = TestCreationFactory.newTreatment();
        TreatmentDTO dto = TreatmentDTO.builder()
                .title(treatment.getTitle())
                .category(treatment.getCategory().getName().name())
                .build();

        when(treatmentRepository.existsByTitle(treatment.getTitle())).thenReturn(true);
        when(treatmentMapper.toDto(treatment)).thenReturn(dto);
        when(treatmentMapper.fromDto(dto)).thenReturn(treatment);
        when(treatmentRepository.save(treatment)).thenReturn(treatment);

        TreatmentDTO created = treatmentService.create(dto);

        Assertions.assertEquals(dto.getTitle(), created.getTitle());
    }
}
