package com.project.clinic.treatment;

import com.project.clinic.treatment.TreatmentMapper;
import com.project.clinic.treatment.TreatmentRepository;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.treatment.model.TreatmentCategory;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;

    public Treatment findById(Long id) {
        return treatmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Treatment not found: " + id));
    }

    public TreatmentDTO create(TreatmentDTO treatment) {
        if(!treatmentRepository.existsByTitle(treatment.getTitle())) {
            return treatmentMapper.toDto(treatmentRepository.save(
                    treatmentMapper.fromDto(treatment).builder()
                            .title(treatment.getTitle())
                            .category(TreatmentCategory.toTreatmentCategory(treatment.getCategory()))
                            .build()
            ));
        }
        else
            return treatment;
    }
}
