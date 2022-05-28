package com.example.airbnb.image;

import com.example.airbnb.image.model.ImageURL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageURLRepository extends JpaRepository<ImageURL, Long> {
}
