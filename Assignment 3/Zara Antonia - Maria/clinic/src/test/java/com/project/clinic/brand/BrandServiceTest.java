package com.project.clinic.brand;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.brand.model.Brand;
import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.ingredient.model.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BrandServiceTest {

    @InjectMocks
    private BrandService brandService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandService = new BrandService(brandRepository, brandMapper);
    }

    @Test
    void findById(){
        Brand brand = TestCreationFactory.newBrand();

        when(brandRepository.findById(brand.getId())).thenReturn(java.util.Optional.of(brand));

        Brand found = brandService.findById(brand.getId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals(brand.getName(), found.getName());
    }

    @Test
    void findAll(){
       List<Brand> brands = TestCreationFactory.listOf(Brand.class);

        when(brandRepository.findAll()).thenReturn(brands);
        for(Brand b: brands) {
            when(brandMapper.toDto(b)).thenReturn(
                    BrandDTO.builder()
                            .id(b.getId())
                            .name(b.getName())
                            .products(new HashSet<>())
                            .build()
            );
        }

        List<BrandDTO> all = brandService.findAll();

        Assertions.assertEquals(brands.size(), all.size());
    }

    @Test
    void create(){
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        Brand brand = Brand.builder()
                .id(brandDTO.getId())
                .name(brandDTO.getName())
                .products(new HashSet<>())
                .build();

        when(brandMapper.fromDto(brandDTO)).thenReturn(brand);
        when(brandMapper.toDto(brand)).thenReturn(brandDTO);
        when(brandRepository.save(brand)).thenReturn(brand);

        BrandDTO saved = brandService.create(brandDTO);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals(brandDTO.getName(), saved.getName());
    }
}
