package com.example.airbnb.amenities.model;

import com.example.airbnb.accommodation.model.Accommodation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name="accommodation_amenities")
public class Amenity {

    @Id
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_fk", nullable = false)
    private Accommodation accommodation;

    @Column(nullable = false, length = 1024)
    private String string;
}
