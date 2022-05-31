package com.example.projectsd.court.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String availability;

    @Column(length = 50, nullable = false)
    private String sportType;


}
