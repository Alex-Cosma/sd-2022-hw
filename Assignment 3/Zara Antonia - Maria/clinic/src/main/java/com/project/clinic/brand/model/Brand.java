package com.project.clinic.brand.model;

import com.project.clinic.product.model.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Getter
@Setter
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "brand")//, orphanRemoval = true)
    private Set<Product> products;
}
