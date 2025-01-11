package com.example.aloevibe.service.impl;

import com.example.aloevibe.model.dto.CategoryDTO;
import com.example.aloevibe.model.entity.Category;
import com.example.aloevibe.model.mapper.CategoryMapper;
import com.example.aloevibe.repository.CategoryRepository;
import com.example.aloevibe.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
