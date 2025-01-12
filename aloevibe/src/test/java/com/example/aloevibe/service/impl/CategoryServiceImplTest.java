package com.example.aloevibe.service.impl;

import com.example.aloevibe.model.dto.CategoryDTO;
import com.example.aloevibe.model.entity.Category;
import com.example.aloevibe.model.mapper.CategoryMapper;
import com.example.aloevibe.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getAllCategories_shouldReturnCategoryDTOs() {
        List<Category> categories = List.of(
                new Category(UUID.randomUUID(), "Category1", "Description1", "url1", new ArrayList<>()),
                new Category(UUID.randomUUID(), "Category2", "Description2", "url2", new ArrayList<>())
        );

        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(category.getId());
                    dto.setName(category.getName());
                    dto.setDescription(category.getDescription());
                    dto.setImageUrl(category.getImageUrl());
                    return dto;
                })
                .toList();

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDTO(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            return categoryDTOs.stream()
                    .filter(dto -> dto.getId().equals(category.getId()))
                    .findFirst()
                    .orElse(null);
        });

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertEquals(categoryDTOs.size(), result.size());
        assertEquals(categoryDTOs.getFirst().getName(), result.getFirst().getName());
    }
}
