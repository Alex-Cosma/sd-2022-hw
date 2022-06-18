package com.app.bookingapp.data.dto.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

@Data
public class PropertyDto implements Serializable {
    private final UserDto owner;
    private final String name;
    private final String address;
    private final Long picturesId;
    private final Float price;
    private final Float rating;
    private final String description;
    private final Long numberOfRooms;
    private final Long numberOfBeds;
    private final Long numberOfBathrooms;
    private final Long kitchen;
    private final byte[] picture;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof PropertyDto)) {
            return false;
        }

        PropertyDto c = (PropertyDto) o;

        return this.owner.equals(c.getOwner())
                && this.name.equals(c.getName())
                && this.address.equals(c.getAddress())
                && this.picturesId.equals(c.getPicturesId())
                && this.price.equals(c.getPrice())
                && this.rating.equals(c.getRating())
                && this.numberOfBathrooms.equals(c.getNumberOfBathrooms())
                && this.numberOfRooms.equals(c.getNumberOfRooms())
                && this.kitchen.equals(c.getKitchen())
                && Arrays.equals(this.picture, c.picture);

    }
}
