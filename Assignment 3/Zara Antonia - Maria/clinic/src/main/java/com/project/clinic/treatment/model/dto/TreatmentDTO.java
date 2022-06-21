package com.project.clinic.treatment.model.dto;

import com.project.clinic.treatment.model.ETreatmentCategory;
import com.project.clinic.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDTO {
    private Long id;
    private String title;
    private Set<User> professionals;
    private String category;
    private int points;
}
