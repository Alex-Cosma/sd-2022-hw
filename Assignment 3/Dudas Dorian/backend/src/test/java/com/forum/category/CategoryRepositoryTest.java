package com.forum.category;

import com.forum.TestCreationFactory;
import com.forum.category.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        Category categorySaved = categoryRepository.save(Category.builder().name("test").build());

        assertNotNull(categorySaved);

        assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(Category.builder().build()));
    }

    @Test
    public void testFindAll() {
        List<Category> categories = TestCreationFactory.listOf(Category.class);
        categoryRepository.saveAll(categories);
        List<Category> categoriesFound = categoryRepository.findAll();
        assertEquals(categories.size(), categoriesFound.size());
    }

    @Test
    public void testDeleteByID() {
        List<Category> categories = TestCreationFactory.listOf(Category.class);
        categoryRepository.saveAll(categories);
        List<Category> categoriesFound = categoryRepository.findAll();
        assertEquals(categories.size(), categoriesFound.size());
        categoryRepository.deleteById(categoriesFound.get(0).getId());
        assertEquals(categories.size() - 1, categoryRepository.findAll().size());
    }

    @Test
    public void testFindByName() {
        Category categorySaved = categoryRepository.save(Category.builder().name("test").description("description").build());

        assertNotNull(categorySaved);

        assertTrue(categoryRepository.existsByName("test"));

        assertFalse(categoryRepository.existsByName("test2"));

        assertEquals(categorySaved.getDescription(), categoryRepository.findByName("test").get().getDescription());
    }
}
