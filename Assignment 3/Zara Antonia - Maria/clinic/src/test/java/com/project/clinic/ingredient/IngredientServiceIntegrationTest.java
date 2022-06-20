package com.project.clinic.ingredient;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.ingredient.model.Ingredient;
import com.project.clinic.ingredient.model.dto.IngredientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class IngredientServiceIntegrationTest {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        ingredientRepository.deleteAll();
    }

    @Test
    void findByTitle(){
        Ingredient ingredient = TestCreationFactory.newIngredient();
        Ingredient saved = ingredientRepository.save(ingredient);

        Ingredient found = ingredientService.findByTitle(ingredient.getTitle());

        Assertions.assertEquals(saved, found);
    }

    @Test
    void create(){
        IngredientDTO ingredient = TestCreationFactory.newIngredientDTO();
        ingredientService.create(ingredient);

        List<Ingredient> all = ingredientRepository.findAll();

        Assertions.assertEquals(1, all.size());
    }
}
