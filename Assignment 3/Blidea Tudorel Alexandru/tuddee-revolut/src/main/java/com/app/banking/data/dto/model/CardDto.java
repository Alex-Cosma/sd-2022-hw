package com.app.banking.data.dto.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto implements Serializable {


    private CardTypeDto cardType;
    private Float currentAmount;
    private String cvv;
    private String number;
    private LocalDate expirationDate;
    private String iban;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof CardDto)) {
            return false;
        }

        CardDto c = (CardDto) o;

        return this.cardType.equals(c.getCardType())
                && this.currentAmount.equals(c.getCurrentAmount())
                && this.cvv.equals(c.getCvv())
                && this.number.equals(c.getNumber())
                && this.expirationDate.equals(c.getExpirationDate());
    }

}
