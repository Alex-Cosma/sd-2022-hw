package com.app.banking.data.sql.entity;

import com.app.banking.util.CsvWriteable;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card_types")
public class CardType implements CsvWriteable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "maxWithdrawal", nullable = false)
    private Integer maxWithdrawal;

    @Column(name = "cashbackPercent", nullable = false)
    private Float cashbackPercent;

    @Column(name = "color", nullable = false, length = 16)
    private String color;


    @Override
    public String getCsvLine() {
        return String.join(", ", List.of(name, maxWithdrawal.toString(), cashbackPercent.toString()));
    }

    @Override
    public String getCsvHeader() {
        return "cardType, maxWithdrawal, cashbackPercent";
    }

}