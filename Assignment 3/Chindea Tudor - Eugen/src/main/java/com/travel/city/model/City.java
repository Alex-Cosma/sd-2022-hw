package com.travel.city.model;

import com.travel.flight.model.Flight;
import com.travel.hotel.model.Hotel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "name"),
//
//        })
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotelList;

    @OneToMany(mappedBy = "departureCity",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flightDepartures;

    @OneToMany(mappedBy = "arrivalCity",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flightArrivals;
}
