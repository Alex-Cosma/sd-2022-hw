package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

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
}
