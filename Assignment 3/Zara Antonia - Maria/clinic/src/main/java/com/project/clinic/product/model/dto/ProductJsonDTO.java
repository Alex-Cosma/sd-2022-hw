package com.project.clinic.product.model.dto;

import com.project.clinic.brand.model.Brand;
import com.project.clinic.ingredient.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductJsonDTO {
    private Long id;
    private String name;
    private String brand;
    private Long brandId;
    private String[] ingredient_list;
    private int price;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", ingredients=");

        return sb.toString();
    }
}
