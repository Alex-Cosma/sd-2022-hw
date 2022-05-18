package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.address.AddressService;
import com.example.airbnb.address.model.Address;
import com.example.airbnb.amenities.AmenityService;
import com.example.airbnb.amenities.model.Amenity;
import com.example.airbnb.image.ImageURLService;
import com.example.airbnb.image.model.ImageURL;
import com.example.airbnb.user.UserService;
import com.example.airbnb.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationMapper accommodationMapper;
    private final AccommodationRepository accommodationRepository;
    private final AddressService addressService;
    private final AmenityService amenityService;
    private final ImageURLService imageURLService;
    private final UserService userService;

    public Page<Accommodation> findAll() {
        Pageable paging = PageRequest.of(0, 3);
        return accommodationRepository.findAll(paging);
    }

    public List<Accommodation> findAllAcc() {
//        Pageable paging = PageRequest.of(0, 3);
        return accommodationRepository.findAll();
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElseGet(null);
    }

    public void delete(Long id){
        accommodationRepository.deleteById(id);
    }

    public Accommodation create(AccommodationDTO accommodationDTO){
        Accommodation accommodation = accommodationMapper.fromDto(accommodationDTO);

        Address address = addressService.create(accommodationDTO.getAddress_street(), accommodationDTO.getAddress_city(), accommodation);
        accommodation.setAddress(address);

        Set<Amenity> amenities = amenityService.createSetOfAmenities(accommodationDTO.getAmenities());
        accommodation.setAmenities(amenities);

        ImageURL imageURL = imageURLService.create(accommodationDTO.getImageURL(), accommodation);
        accommodation.setImageURL(imageURL);

        User user = userService.findById(accommodationDTO.getUser_id());
        accommodation.setUser(user);

        return accommodationRepository.save(accommodation);
    }

}
