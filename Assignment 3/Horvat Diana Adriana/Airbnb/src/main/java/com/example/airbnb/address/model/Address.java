package com.example.airbnb.address.model;

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
@Table(name="accommodation_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 256)
    private String street;

    @Column(nullable = false, length = 256)
    private String city;

    @JsonIgnore
    @OneToOne(mappedBy = "address", cascade=CascadeType.ALL)
    private Accommodation accommodation;
}
