package com.forum.category;

import com.forum.category.mapper.CategoryMapper;
import com.forum.category.model.Category;
import com.forum.category.model.dto.CategoryDTO;
import com.forum.post.PostRepository;
import com.forum.thread.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.forum.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TopicRepository topicRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryRepository, categoryMapper, postRepository, topicRepository);
    }

    @Test
    void findAll() {
        List<Category> categories = listOf(Category.class);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> all = categoryService.findAll();

        assertEquals(categories.size(), all.size());
    }

    @Test
    void findById(){
        Category category = newCategory();
        when(categoryRepository.findById(category.getId())).thenReturn(java.util.Optional.of(category));

        given(categoryMapper.toDto(category)).willReturn(
                CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build()
        );

        CategoryDTO categoryDTO = categoryService.findCategoryById(category.getId());

        assertEquals(category.getId(), categoryDTO.getId());
    }

    @Test
    void testCreateCategory(){
        Category category = newCategory();
        when(categoryRepository.save(category)).thenReturn(category);

        given(categoryMapper.toDto(category)).willReturn(
                CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build()
        );

        given(categoryMapper.fromDto(categoryMapper.toDto(category))).willReturn(category);

        CategoryDTO result = categoryService.create(categoryMapper.toDto(category));

        assertEquals(category.getName(), result.getName());
    }

    @Test
    void testEditCategory(){
        Category category = newCategory();
        Category category1 = newCategory();
        when(categoryRepository.findById(category.getId())).thenReturn(java.util.Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        given(categoryMapper.toDto(category1)).willReturn(
                CategoryDTO.builder()
                        .id(category1.getId())
                        .name(category1.getName())
                        .description(category1.getDescription())
                        .build()
        );

        when(categoryService.edit(category.getId(), categoryMapper.toDto(category1))).thenReturn(
                CategoryDTO.builder()
                        .id(category1.getId())
                        .name(category1.getName())
                        .description(category1.getDescription())
                        .build()
        );

        CategoryDTO result = categoryService.edit(category.getId(), categoryMapper.toDto(category1));

        assertEquals(category1.getId(), result.getId());
        assertEquals(category1.getName(), result.getName());
    }

    @Test
    void testDeleteCategory(){
        Category category = newCategory();
        when(categoryRepository.findById(category.getId())).thenReturn(java.util.Optional.of(category));

        given(categoryMapper.toDto(category)).willReturn(
                CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build()
        );

        CategoryDTO result = categoryService.delete(category.getId());

        assertEquals(category.getName(), result.getName());
    }
}
