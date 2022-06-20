package com.project.clinic.product;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.brand.BrandService;
import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.product.model.Product;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void findById(){
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        brandDTO = brandService.create(brandDTO);

        Product original = TestCreationFactory.newProduct();
        original.setBrand(brandService.findById(brandDTO.getId()));
        original.setIngredients(new HashSet<>());

        Product saved = productRepository.save(original);

        Product found = productService.findById(saved.getId());

        Assertions.assertEquals(original.getName(), found.getName());
        Assertions.assertEquals(original.getBrand().getName(), found.getBrand().getName());
        Assertions.assertEquals(original.getPrice(), found.getPrice());
    }

    @Test
    void findDTOById(){
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        brandDTO = brandService.create(brandDTO);

        Product original = TestCreationFactory.newProduct();
        original.setBrand(brandService.findById(brandDTO.getId()));
        original.setIngredients(new HashSet<>());

        Product saved = productRepository.save(original);

        ProductJsonDTO found = productService.findDTOById(saved.getId());

        Assertions.assertEquals(original.getName(), found.getName());
        Assertions.assertEquals(original.getBrand().getName(), found.getBrand());
        Assertions.assertEquals(original.getPrice(), found.getPrice());
    }

    @Test
    void findAll() {
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        brandDTO = brandService.create(brandDTO);

        List<ProductJsonDTO> products = TestCreationFactory.listOf(ProductJsonDTO.class);
        for(ProductJsonDTO p: products) {
            Product product = productMapper.fromJsonDto(p);
            product.setBrand(brandService.findById(brandDTO.getId()));
            productRepository.save(product);
        }

        List<ProductJsonDTO> all = productService.findAll();

        Assertions.assertEquals(products.size(), all.size());
    }

    @Test
    void create(){
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        brandDTO = brandService.create(brandDTO);

        Product original = TestCreationFactory.newProduct();
        original.setBrand(brandService.findById(brandDTO.getId()));
        original.setIngredients(new HashSet<>());

        ProductJsonDTO originalDTO = productMapper.toJsonDto(original);
        originalDTO.setIngredient_list(new String[0]);
        productService.create(originalDTO);

        List<ProductJsonDTO> all = productService.findAll();

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void edit(){
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        brandDTO = brandService.create(brandDTO);

        Product original = TestCreationFactory.newProduct();
        original.setBrand(brandService.findById(brandDTO.getId()));
        original.setIngredients(new HashSet<>());

        ProductJsonDTO originalDTO = productMapper.toJsonDto(original);
        originalDTO.setIngredient_list(new String[0]);

        ProductJsonDTO saved = productService.create(originalDTO);

        String newName = TestCreationFactory.randomString();
        saved.setName(newName);

        ProductJsonDTO updated = productService.edit(saved.getId(), saved);

        Assertions.assertEquals(newName, updated.getName());
        Assertions.assertEquals(saved.getPrice(), updated.getPrice());
    }

    @Test
    void delete(){
        BrandDTO brandDTO = TestCreationFactory.newBrandDTO();
        brandDTO = brandService.create(brandDTO);

        Product original = TestCreationFactory.newProduct();
        original.setBrand(brandService.findById(brandDTO.getId()));
        original.setIngredients(new HashSet<>());

        ProductJsonDTO originalDTO = productMapper.toJsonDto(original);
        originalDTO.setIngredient_list(new String[0]);

        ProductJsonDTO saved = productService.create(originalDTO);

        List<ProductJsonDTO> all = productService.findAll();
        Assertions.assertEquals(1, all.size());

        productService.delete(saved.getId());

        all = productService.findAll();
        Assertions.assertEquals(0, all.size());
    }

}
