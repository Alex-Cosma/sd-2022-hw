package com.project.clinic.treatment;

import com.project.clinic.treatment.model.TreatmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentCategoryRepository extends JpaRepository<TreatmentCategory, Long> {
}
