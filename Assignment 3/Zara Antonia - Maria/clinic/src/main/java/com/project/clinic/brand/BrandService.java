package com.project.clinic.brand;

import com.project.clinic.brand.model.Brand;
import com.project.clinic.brand.model.dto.BrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + id));
    }

    public List<BrandDTO> findAll() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    public BrandDTO create(BrandDTO brand) {
        return brandMapper.toDto(brandRepository.save(
                brandMapper.fromDto(brand)
        ));
    }

}
