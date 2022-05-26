package com.example.leaguecomp.rune.model;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class Rune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1024)
    private String description;

}
