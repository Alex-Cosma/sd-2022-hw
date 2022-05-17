package com.raulp.user.model;

import com.raulp.flight.Flight;
import com.raulp.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Instructor extends User {
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private Set<Flight> flights = new HashSet<>();

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();
}
