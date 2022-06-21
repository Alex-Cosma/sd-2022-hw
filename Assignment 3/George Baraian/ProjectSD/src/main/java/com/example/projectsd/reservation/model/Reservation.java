package com.example.projectsd.reservation.model;

import com.example.projectsd.court.model.Court;
import com.example.projectsd.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private Date startTime;

    @Column(length = 50)
    private  Date endTime;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User client;

    @ManyToOne
    @JoinColumn(name = "court")
    private Court court;


}
