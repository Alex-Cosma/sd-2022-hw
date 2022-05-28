package com.example.airbnb.image.model;

import com.example.airbnb.accommodation.model.Accommodation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.print.DocFlavor;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name="accommodation_images")
public class ImageURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToOne(mappedBy = "imageURL")
    private Accommodation accommodation;

    @Column(nullable = false, columnDefinition="TEXT")
    private String picture_url;
}
