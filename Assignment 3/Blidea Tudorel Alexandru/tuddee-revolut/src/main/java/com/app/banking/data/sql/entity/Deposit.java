package com.app.banking.data.sql.entity;

import com.app.banking.util.CsvWriteable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ownerId", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "depositTypeId", nullable = false)
    private DepositType depositType;

    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "currentAmount", nullable = false)
    private Float currentAmount;

    @Column(name = "targetDate", nullable = false)
    private LocalDate targetDate;

    @Column(name = "targetAmount", nullable = false)
    private Float targetAmount;

}