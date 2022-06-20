package com.project.clinic.treatment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TreatmentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ETreatmentCategory name;


    public static TreatmentCategory toTreatmentCategory(String s) {
        return new TreatmentCategory.TreatmentCategoryBuilder().name(ETreatmentCategory.valueOf(s)).build();
    }
}
