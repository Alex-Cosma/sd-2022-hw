package com.app.banking.data.sql.entity;

import com.app.banking.util.CsvWriteable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses_on_month")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpensesOnMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ownerId", nullable = false)
    private Business owner;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "expenseId", nullable = false)
    private Expens expense;

    @Column(name = "onDate", nullable = false)
    private LocalDateTime onDate;

    @Column(name = "currentValue", nullable = false)
    private Float currentValue;

}