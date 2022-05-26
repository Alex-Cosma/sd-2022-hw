package com.raulp.user.model;

import com.raulp.flight.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Instructor extends User {
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flight> flights = new HashSet<>();

//    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER)
//    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();
}
