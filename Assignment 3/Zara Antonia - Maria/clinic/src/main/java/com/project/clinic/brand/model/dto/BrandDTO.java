package com.project.clinic.brand.model.dto;

import com.project.clinic.product.model.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandDTO {
    private Long id;
    private String name;
    private Set<String> products;
}
