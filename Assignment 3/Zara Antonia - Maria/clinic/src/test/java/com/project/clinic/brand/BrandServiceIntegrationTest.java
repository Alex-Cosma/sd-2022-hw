package com.project.clinic.brand;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.brand.model.Brand;
import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.ingredient.IngredientRepository;
import com.project.clinic.product.ProductRepository;
import com.project.clinic.product.model.Product;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class BrandServiceIntegrationTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        brandRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    void findById(){
        Brand brand = TestCreationFactory.newBrand();
        Brand saved = brandRepository.save(brand);

        Brand found = brandService.findById(saved.getId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals(saved.getId(), found.getId());
        Assertions.assertEquals(saved.getName(), found.getName());
    }

    @Test
    void findAll(){
        List<Brand> brands = TestCreationFactory.listOf(Brand.class);
        brandRepository.saveAll(brands);

        List<BrandDTO> all = brandService.findAll();

        Assertions.assertEquals(brands.size(), all.size());
    }

    @Test
    void create(){
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        BrandDTO saved = brandService.create(brandDTO);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals(brandDTO.getName(),saved.getName());
        Assertions.assertEquals(1, brandRepository.findAll().size());
    }

}
