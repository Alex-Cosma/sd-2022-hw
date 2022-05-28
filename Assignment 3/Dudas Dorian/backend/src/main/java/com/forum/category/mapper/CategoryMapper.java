package com.forum.category.mapper;

import com.forum.category.model.Category;
import com.forum.category.model.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);

    Category fromDto(CategoryDTO categoryDTO);
}
