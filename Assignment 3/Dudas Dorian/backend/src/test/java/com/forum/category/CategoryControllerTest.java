package com.forum.category;

import com.forum.BaseControllerTest;
import com.forum.category.model.dto.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.forum.TestCreationFactory.*;
import static com.forum.UrlMapping.CATEGORY;
import static com.forum.UrlMapping.CATEGORY_ID_PART;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends BaseControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        categoryController = new CategoryController(categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void allItems() throws Exception {
        List<CategoryDTO> categories = listOf(CategoryDTO.class);
        when(categoryService.findAll()).thenReturn(categories);

        ResultActions response = performGet(CATEGORY);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(categories));
    }

    @Test
    void getById() throws Exception {
        CategoryDTO category = newCategoryDTO();
        when(categoryService.findCategoryById(category.getId())).thenReturn(category);

        ResultActions response = performGetWithPathVariable(CATEGORY + CATEGORY_ID_PART, category.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(category));
    }

    @Test
    void create() throws Exception {
        CategoryDTO category = newCategoryDTO();
        when(categoryService.create(category)).thenReturn(category);

        ResultActions response = performPostWithRequestBody(CATEGORY, category);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(category));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        CategoryDTO category = newCategoryDTO();
        when(categoryService.edit(id, category)).thenReturn(category);

        ResultActions response = performPutWithRequestBodyAndPathVariables(CATEGORY + CATEGORY_ID_PART, category, id);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(category));
    }

    @Test
    void delete() throws Exception {
        final CategoryDTO category = newCategoryDTO();
        when(categoryService.delete(category.getId())).thenReturn(category);

        ResultActions response = performDeleteWithPathVariables(CATEGORY + CATEGORY_ID_PART, category.getId());

        response.andExpect(status().isOk());
    }
}
