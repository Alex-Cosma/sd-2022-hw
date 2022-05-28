package com.example.backend.game.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512)
    private String title;

    @Column(length = 1024)
    private String thumbnail;

    @Column(length = 1024)
    private String description;

    @Column(length = 1024)
    private String gameURL;

    @Column(length = 1024)
    private String genre;

    @Column(length = 1024)
    private String platform;

    @Column(length = 1024)
    private String publisher;

    @Column(length = 1024)
    private String developer;

    @Column(length = 1024)
    private String releaseDate;

    @Column(length = 1024)
    private String freeToGameProfileURL;

   /* @OneToMany(mappedBy = "item")
    @Builder.Default
    private Set<Review> reviews = new HashSet<>();*/
}
