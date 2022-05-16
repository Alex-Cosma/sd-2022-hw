package com.example.airbnb.review.model;

import com.example.airbnb.accommodation.model.Accommodation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name="reviews")
public class Review {
    @Id
    private Integer id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "object_fk", nullable = false)
//    private Accommodation accommodation;

    @Column(nullable = false, length = 256)
    private String reviewer_name;

    @Column(nullable = false, length=5000)
    private String comments;
}
