package com.app.banking.data.sql.entity;

import com.app.banking.util.CsvWriteable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card implements CsvWriteable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ownerId", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cardTypeId", nullable = false)
    private CardType cardType;

    @Column(name = "currentAmount", nullable = false)
    private Float currentAmount;

    @Column(name = "cvv", length = 3)
    private String cvv;

    @Column(name = "number", nullable = false, length = 16)
    private String number;

    @Column(name = "expirationDate", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "iban", nullable = false, length = 34)
    private String iban;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "statusId", nullable = false)
    private CardStatus cardStatus;

    @Override
    public String getCsvLine() {
        return String.join(", ", List.of(owner.getEmail(), cardType.getCsvLine(), currentAmount.toString(),
                cvv, number, expirationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), iban, cardStatus.getName()));
    }

    @Override
    public String getCsvHeader() {
        return "owner email, " + cardType.getCsvHeader() + ", current amount, cvv, number, expiration date, iban, status";
    }

}