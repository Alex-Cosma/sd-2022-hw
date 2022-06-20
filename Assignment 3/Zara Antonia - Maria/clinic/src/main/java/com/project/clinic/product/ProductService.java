package com.project.clinic.product;

import com.project.clinic.brand.BrandService;
import com.project.clinic.ingredient.IngredientService;
import com.project.clinic.ingredient.model.Ingredient;
import com.project.clinic.product.model.Product;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandService brandService;
    private final IngredientService ingredientService;

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    public ProductJsonDTO findDTOById(Long id){
        return productMapper.toJsonDto(
                productRepository.findById(id).get()
        );
    }


    public List<ProductJsonDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toJsonDto)
                .map(x -> {
                    x.setIngredient_list
                            (productRepository.findById(x.getId()).get().getIngredients().stream()
                                    .map(Ingredient::getTitle)
                                    .toArray(String[]::new));
                    return x;
                })
                .collect(Collectors.toList());
    }

    public ProductJsonDTO create(ProductJsonDTO product) {
        return productMapper.toJsonDto(productRepository.save(
                productMapper.fromJsonDto(product).builder()
                        .price(product.getPrice())
                        .name(product.getName())
                        .brand(brandService.findById(product.getBrandId()))
                        .ingredients(Arrays.stream(product.getIngredient_list())
                                        .map(ingredientService::findByTitle)
                                        .collect(Collectors.toSet()))
                .build()));
    }

    public ProductJsonDTO edit(Long id, ProductJsonDTO product) {
        Product actProduct = new Product().builder()
                .id(id)
                .price(product.getPrice())
                .name(product.getName())
                .ingredients(new HashSet<>())
                .build();

        if(product.getBrandId() != null){
            actProduct.setBrand(brandService.findById(product.getBrandId()));
        }
        else {
            actProduct.setBrand(brandService.findById(findById(id).getBrand().getId()));
        }

        return productMapper.toJsonDto(
                productRepository.save(actProduct)
        );
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
