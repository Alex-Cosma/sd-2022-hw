package com.project.clinic.treatment.model;

import com.project.clinic.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Getter
@Setter
@Table(name = "treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @ManyToOne(cascade=CascadeType.ALL)
    private TreatmentCategory category;

    @Column(nullable = false)
    private int points;
}
