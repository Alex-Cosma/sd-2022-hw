package com.app.banking.data.dto.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardTypeDto implements Serializable {

    private final Float epsilonError = 0.000001f;
    private String name;
    private String description;
    private Integer maxWithdrawal;
    private Float cashbackPercent;
    private String color;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof CardTypeDto)) {
            return false;
        }

        CardTypeDto c = (CardTypeDto) o;

        return this.name.equals(c.getName())
                && this.description.equals(c.getDescription())
                && this.maxWithdrawal.equals(c.getMaxWithdrawal())
                && Math.abs(this.cashbackPercent - c.getCashbackPercent()) < this.epsilonError;
    }
}
