package com.travel.BookingFlight.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.flight.model.Flight;
import com.travel.hotel.model.Hotel;
import com.travel.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class BookingFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column( nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer seats;



    @ManyToOne
    @JoinColumn(name="flight_id")
    private Flight flight;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bookingFlight_user",
            joinColumns = @JoinColumn(name = "bookingFlight_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> users = new HashSet<>();
}