package com.travel.hotel.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travel.BookingHotel.model.BookingHotel;
import com.travel.city.model.City;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;


    @Column( nullable = false)
    private Integer places;

    @Column(nullable = false)
    private Integer price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingHotel> bookingList;
}
