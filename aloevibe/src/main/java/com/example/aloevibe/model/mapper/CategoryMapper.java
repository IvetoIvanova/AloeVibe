package com.example.aloevibe.model.mapper;

import com.example.aloevibe.model.dto.CategoryDTO;
import com.example.aloevibe.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}
