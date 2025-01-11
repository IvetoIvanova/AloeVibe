package com.example.aloevibe.service;

import com.example.aloevibe.model.dto.CategoryDTO;
import com.example.aloevibe.model.entity.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();
}
