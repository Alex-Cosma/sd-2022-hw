package com.project.clinic.ingredient.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Getter
@Setter
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;
}
