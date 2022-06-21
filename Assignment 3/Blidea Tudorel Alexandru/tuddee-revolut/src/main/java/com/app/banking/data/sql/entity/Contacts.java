package com.app.banking.data.sql.entity;

import com.app.banking.data.sql.entity.enums.RequestStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "person1Id")
    private Integer person1Id;

    @Column(name = "person2Id")
    private Integer person2Id;

    @Enumerated(EnumType.STRING)
    @Column(name = "requestStatus")
    private RequestStatus requestStatus;

    @Column(name = "acceptedOn")
    private LocalDate acceptedOn;

}
