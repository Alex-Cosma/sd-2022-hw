package com.forum.category;

import com.forum.category.model.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.forum.UrlMapping.*;

@RestController
@RequestMapping(CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> allCategories() {
        return categoryService.findAll();
    }

    @GetMapping(CATEGORY_ID_PART)
    public CategoryDTO categoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO category) {
        return categoryService.create(category);
    }

    @PutMapping(CATEGORY_ID_PART)
    public CategoryDTO edit(@PathVariable Long id, @RequestBody CategoryDTO category) {
        return categoryService.edit(id, category);
    }

    @DeleteMapping(CATEGORY_ID_PART)
    public CategoryDTO delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }
}
