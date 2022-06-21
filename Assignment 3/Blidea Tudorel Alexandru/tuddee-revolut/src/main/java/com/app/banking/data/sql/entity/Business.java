package com.app.banking.data.sql.entity;

import com.app.banking.util.CsvWriteable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "businesses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "CIF", nullable = false, length = 12)
    private String cif;

    @Column(name = "balance", nullable = false)
    private Float balance;

}