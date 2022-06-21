package com.project.clinic.ingredient;

import com.project.clinic.ingredient.model.Ingredient;
import com.project.clinic.ingredient.model.dto.IngredientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public Ingredient findByTitle(String title){
        return ingredientRepository.findByTitle(title);
    }

    public IngredientDTO create(IngredientDTO ingredient) {
        if(!ingredientRepository.existsByTitle(ingredient.getTitle()))
            return ingredientMapper.toDto(ingredientRepository.save(
                    ingredientMapper.fromDto(ingredient)
            ));
        else
            return ingredient;
    }
}
