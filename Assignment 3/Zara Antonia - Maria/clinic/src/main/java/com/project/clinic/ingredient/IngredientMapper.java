package com.project.clinic.ingredient;

import com.project.clinic.ingredient.model.Ingredient;
import com.project.clinic.ingredient.model.dto.IngredientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    Ingredient fromDto(IngredientDTO ingredient);

    IngredientDTO toDto(Ingredient ingredient);
}
