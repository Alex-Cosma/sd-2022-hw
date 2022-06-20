package com.project.clinic.product;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.brand.BrandService;
import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.ingredient.IngredientService;
import com.project.clinic.product.model.Product;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static com.project.clinic.URLMapping.GET_PRODUCTS;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private BrandService brandService;

    @Mock
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, productMapper, brandService, ingredientService);
    }

    @Test
    void findById(){
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        when(productRepository.findById(productDto.getId())).thenReturn(java.util.Optional.of(product));

        Product found = productService.findById(productDto.getId());

        Assertions.assertEquals(productDto.getName(), found.getName());
    }

    @Test
    void findDTOById(){
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        when(productMapper.toJsonDto(product)).thenReturn(productDto);
        when(productRepository.findById(productDto.getId())).thenReturn(java.util.Optional.of(product));

        ProductJsonDTO found = productService.findDTOById(productDto.getId());

        Assertions.assertEquals(productDto.getName(), found.getName());
    }

    @Test
    void findAll() {
        Product product = TestCreationFactory.newProduct();
        Product product2 = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        ProductJsonDTO productDto2 = ProductJsonDTO.builder()
                .id(2L)
                .name(product2.getName())
                .price(product2.getPrice())
                .brand(product2.getBrand().getName())
                .brandId(product2.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toJsonDto(product)).thenReturn(productDto);
        when(productMapper.toJsonDto(product2)).thenReturn(productDto2);
        when(productRepository.findById(productDto.getId())).thenReturn(java.util.Optional.of(product));
        when(productRepository.findById(productDto2.getId())).thenReturn(java.util.Optional.of(product2));

        List<ProductJsonDTO> all = productService.findAll();

        Assertions.assertEquals(2, all.size());
    }

    @Test
    void create(){
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        ArrayList<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toJsonDto(product)).thenReturn(productDto);
        when(productRepository.findById(productDto.getId())).thenReturn(java.util.Optional.of(product));
        when(brandService.findById(product.getBrand().getId())).thenReturn(product.getBrand());
        when(productRepository.save(productMapper.fromJsonDto(productDto).builder()
                .price(productDto.getPrice())
                .name(productDto.getName())
                .brand(brandService.findById(productDto.getBrandId()))
                .ingredients(Arrays.stream(productDto.getIngredient_list())
                        .map(ingredientService::findByTitle)
                        .collect(Collectors.toSet()))
                .build()))
                .thenReturn(product);

        ProductJsonDTO saved = productService.create(productDto);

        Assertions.assertEquals(productDto.getName(), saved.getName());
    }

    @Test
    void edit(){
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        ArrayList<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toJsonDto(product)).thenReturn(productDto);
        when(productRepository.findById(productDto.getId())).thenReturn(java.util.Optional.of(product));
        when(brandService.findById(product.getBrand().getId())).thenReturn(product.getBrand());
        when(productRepository.save(productMapper.fromJsonDto(productDto).builder()
                .price(productDto.getPrice())
                .name(productDto.getName())
                .brand(brandService.findById(productDto.getBrandId()))
                .ingredients(Arrays.stream(productDto.getIngredient_list())
                        .map(ingredientService::findByTitle)
                        .collect(Collectors.toSet()))
                .build()))
                .thenReturn(product);

        ProductJsonDTO saved = productService.create(productDto);

        String newName = TestCreationFactory.randomString();
        saved.setName(newName);

        ProductJsonDTO productDto2 = ProductJsonDTO.builder()
                .id(saved.getId())
                .name(newName)
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        Product expected = product;
        expected.setName(newName);

        when(productRepository.save(new Product().builder()
                .id(saved.getId())
                .price(saved.getPrice())
                .name(saved.getName())
                .ingredients(new HashSet<>())
                .brand(product.getBrand())
                .build()))
                .thenReturn(expected);
        when(productMapper.toJsonDto(expected)).thenReturn(productDto2);

        ProductJsonDTO updated = productService.edit(saved.getId(), saved);

        Assertions.assertEquals(newName, updated.getName());
    }

    @Test
    void delete(){
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        ArrayList<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toJsonDto(product)).thenReturn(productDto);
        when(productRepository.findById(productDto.getId())).thenReturn(java.util.Optional.of(product));
        when(brandService.findById(product.getBrand().getId())).thenReturn(product.getBrand());
        when(productRepository.save(productMapper.fromJsonDto(productDto).builder()
                .price(productDto.getPrice())
                .name(productDto.getName())
                .brand(brandService.findById(productDto.getBrandId()))
                .ingredients(Arrays.stream(productDto.getIngredient_list())
                        .map(ingredientService::findByTitle)
                        .collect(Collectors.toSet()))
                .build()))
                .thenReturn(product);

        ProductJsonDTO saved = productService.create(productDto);

        try {
            productService.delete(saved.getId());
        }
        catch (Exception e){
            fail("Should not have thrown exception");
        }
    }
}
