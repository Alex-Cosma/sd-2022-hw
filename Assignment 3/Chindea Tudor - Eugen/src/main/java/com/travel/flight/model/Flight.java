package com.travel.flight.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingHotel.model.BookingHotel;
import com.travel.city.model.City;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false)
    private Integer price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="departureCity_id")
    private City departureCity;


    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingFlight> bookingList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="arrivalCity_id")
    private City arrivalCity;
}
