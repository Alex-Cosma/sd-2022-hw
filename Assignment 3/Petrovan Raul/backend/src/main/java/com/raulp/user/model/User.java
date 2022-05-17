package com.raulp.user.model;

import com.raulp.flight.Flight;
import com.raulp.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        },
        name="\"user\"")
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Email
    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

//    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
//    private Set<Flight> flightsStudent = new HashSet<>();
//
//    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
//    private Set<Flight> flightsInstructor = new HashSet<>();
//
//    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
//    private Set<Reservation> reservationsStudent = new HashSet<>();
//
//    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
//    private Set<Reservation> reservationsInstructor = new HashSet<>();

}
