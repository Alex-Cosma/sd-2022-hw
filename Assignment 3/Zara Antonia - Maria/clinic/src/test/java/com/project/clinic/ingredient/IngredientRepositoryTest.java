package com.project.clinic.ingredient;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.ingredient.model.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IngredientRepositoryTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void beforeEach() {
        ingredientRepository.deleteAll();
    }

    @Test
    void existsByTitle(){
        Ingredient ingredient = TestCreationFactory.newIngredient();
        ingredientRepository.save(ingredient);

        Assertions.assertEquals(true, ingredientRepository.existsByTitle(ingredient.getTitle()));
    }

    @Test
    void findByTitle(){
        Ingredient ingredient = TestCreationFactory.newIngredient();
        ingredientRepository.save(ingredient);

        Ingredient found = ingredientRepository.findByTitle(ingredient.getTitle());

        Assertions.assertNotNull(found);
        Assertions.assertEquals(ingredient.getTitle(), found.getTitle());
    }
}
