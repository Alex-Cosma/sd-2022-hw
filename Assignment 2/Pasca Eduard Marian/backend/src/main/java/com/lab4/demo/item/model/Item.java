package com.lab4.demo.item.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 512, nullable = false)
    private String genre;

    @Column(length = 8000)
    private String description;

    @Column(nullable = true)
    private Long quantity;

    @Column(nullable = true)
    private Long price;
}
