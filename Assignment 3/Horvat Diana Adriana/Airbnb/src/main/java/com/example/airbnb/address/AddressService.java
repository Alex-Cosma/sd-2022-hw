package com.example.airbnb.address;

import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.address.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address create(String street, String city, Accommodation accommodation){
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
//        address.setAccommodation(accommodation);

        return address;
//        return addressRepository.save(address);
    }
}
