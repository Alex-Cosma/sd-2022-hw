package com.project.clinic.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.clinic.URLMapping.INGREDIENT;

@RestController
@RequestMapping(INGREDIENT)
@RequiredArgsConstructor
@CrossOrigin
public class IngredientController {
}
