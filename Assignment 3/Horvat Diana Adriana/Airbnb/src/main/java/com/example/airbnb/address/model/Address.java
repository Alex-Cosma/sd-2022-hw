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
    private Integer id;

    @Column(nullable = false, length = 256)
    private String street;

    @Column(nullable = false, length = 256)
    private String city;

    @Column(nullable = false, length = 256)
    private String state;

    @Column(nullable = false, length = 256)
    private String country;

    @Column(nullable = false, length = 256)
    private String market;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Accommodation accommodation;
}
