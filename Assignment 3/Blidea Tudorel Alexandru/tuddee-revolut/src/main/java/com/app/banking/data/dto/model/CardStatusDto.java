package com.app.banking.data.dto.model;

import com.app.banking.data.sql.entity.enums.ECardStatus;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusDto implements Serializable {
    private ECardStatus name;
    private String description;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof CardStatusDto)) {
            return false;
        }

        CardStatusDto c = (CardStatusDto) o;

        return this.name.equals(c.getName())
                && this.description.equals(c.getDescription());
    }
}
