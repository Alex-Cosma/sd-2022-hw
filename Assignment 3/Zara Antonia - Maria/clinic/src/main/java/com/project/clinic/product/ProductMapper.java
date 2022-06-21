package com.project.clinic.product;

import com.project.clinic.product.model.Product;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "brand", ignore = true),
            @Mapping(target = "ingredients", ignore = true)
    })
    Product fromJsonDto(ProductJsonDTO product);

    @Mappings({
            @Mapping(target = "brand", source = "brand.name"),
            @Mapping(target = "brandId", source = "brand.id"),
            @Mapping(target = "ingredient_list", ignore = true)
    })
    ProductJsonDTO toJsonDto(Product product);


}
