package com.project.clinic.product.model;


import com.project.clinic.brand.model.Brand;
import com.project.clinic.ingredient.model.Ingredient;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="brand_id", nullable=false)
    private Brand brand;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ingredient> ingredients;

    @Column
    private int price;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand=" + brand);

        for(Ingredient ingredient: ingredients){
            sb.append(" " + ingredient.getTitle() + ", ");
        }

        return sb.toString();
    }
}
