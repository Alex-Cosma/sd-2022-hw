package com.example.airbnb.image;

import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.image.model.ImageURL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageURLService {
    private final ImageURLRepository imageURLRepository;

//    public ImageURL create(String picture_url, Accommodation accommodation){
//        ImageURL imageURL = new ImageURL();
//        imageURL.setPicture_url(picture_url);
//
//        return imageURL;
//    }
}
