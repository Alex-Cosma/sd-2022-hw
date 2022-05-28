package com.forum.category;

import com.forum.category.mapper.CategoryMapper;
import com.forum.category.model.Category;
import com.forum.category.model.dto.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.forum.TestCreationFactory.listOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class CategoryServiceIntegrationTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setup() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Category> categories = listOf(Category.class);
        categoryRepository.saveAll(categories);

        List<CategoryDTO> categoriesFound = categoryService.findAll();

        assertEquals(categories.size(), categoriesFound.size());
    }

    @Test
    public void testFindById() {
        List<Category> categories = listOf(Category.class);
        categoryRepository.saveAll(categories);
        List<CategoryDTO> categoriesSaved = categoryService.findAll();

        Category categoryFound = categoryService.findById(categoriesSaved.get(0).getId());

        assertEquals(categories.get(0).getName(), categoryFound.getName());
    }

    @Test
    public void testSave() {
        Category category = Category.builder().name("test").build();

        CategoryDTO categorySaved = categoryService.create(categoryMapper.toDto(category));

        assertEquals(category.getName(), categorySaved.getName());
    }

    @Test
    public void testUpdate() {
        Category category = Category.builder().name("test").build();
        categoryRepository.save(category);

        CategoryDTO categoryUpdated = categoryService.edit(category.getId(), CategoryDTO.builder().name("test2").build());

        assertNotEquals(category.getName(), categoryUpdated.getName());
    }

    @Test
    public void testDelete() {
        List<Category> categories = listOf(Category.class);
        categoryRepository.saveAll(categories);

        List<CategoryDTO> categoriesFound = categoryService.findAll();
        assertEquals(categories.size(), categoriesFound.size());

        categoryService.delete(categoriesFound.get(0).getId());

        assertEquals(categories.size() - 1, categoryService.findAll().size());
    }
}
