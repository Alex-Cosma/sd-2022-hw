package com.project.clinic.ingredient;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.ingredient.model.Ingredient;
import com.project.clinic.ingredient.model.dto.IngredientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientService(ingredientRepository, ingredientMapper);
    }

    @Test
    void findByTitle(){
        Ingredient ingredient = TestCreationFactory.newIngredient();

        when(ingredientRepository.findByTitle(ingredient.getTitle())).thenReturn(ingredient);

        Ingredient found = ingredientService.findByTitle(ingredient.getTitle());

        Assertions.assertEquals(ingredient.getTitle(), found.getTitle());
    }

    @Test
    void create(){
        IngredientDTO ingredientDto = TestCreationFactory.newIngredientDTO();

        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .title(ingredientDto.getTitle())
                .build();

        when(ingredientRepository.existsByTitle(ingredientDto.getTitle())).thenReturn(false);
        when(ingredientMapper.fromDto(ingredientDto)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.toDto(ingredient)).thenReturn(ingredientDto);

        IngredientDTO saved = ingredientService.create(ingredientDto);

        Assertions.assertEquals(ingredientDto.getTitle(), saved.getTitle());

    }
}
