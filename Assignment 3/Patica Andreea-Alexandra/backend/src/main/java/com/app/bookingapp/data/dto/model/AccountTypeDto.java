package com.app.bookingapp.data.dto.model;

import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountTypeDto implements Serializable {
    private final EAccountType name;
    private final String description;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof AccountTypeDto)) {
            return false;
        }

        AccountTypeDto c = (AccountTypeDto) o;

        return this.name.equals(c.getName())
                && this.getDescription().equals(c.getDescription());
    }
}
