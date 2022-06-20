package com.project.clinic.treatment;

import com.project.clinic.treatment.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    boolean existsByTitle(String title);
}
