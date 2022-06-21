package com.project.clinic.ingredient;

import com.project.clinic.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    boolean existsByTitle(String s);

    Ingredient findByTitle(String title);
}
