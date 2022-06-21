package com.project.clinic.appointment.model;

import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Getter
@Setter
@Table(name = "appointments")

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private User dermatologist;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private Treatment treatment;


}
