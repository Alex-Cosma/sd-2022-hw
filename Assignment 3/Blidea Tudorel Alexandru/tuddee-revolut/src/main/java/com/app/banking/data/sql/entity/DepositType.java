package com.app.banking.data.sql.entity;

import com.app.banking.data.sql.entity.enums.EDepositType;
import com.app.banking.util.CsvWriteable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "deposit_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "interestRate", nullable = false)
    private Float interestRate;

}