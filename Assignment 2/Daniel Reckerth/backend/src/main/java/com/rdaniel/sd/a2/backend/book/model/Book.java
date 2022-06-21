package com.rdaniel.sd.a2.backend.book.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(length = 512, nullable = false)
  private String title;

  @Column(length = 256, nullable = false)
  private String author;

  @Column(length = 128, nullable = false)
  private String genre;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private double price;
}
