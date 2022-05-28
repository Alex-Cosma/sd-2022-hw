package com.app.movie.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EGenre name ;
}
