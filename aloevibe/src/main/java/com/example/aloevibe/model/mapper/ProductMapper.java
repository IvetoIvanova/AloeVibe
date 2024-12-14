package com.example.aloevibe.model.mapper;

import com.example.aloevibe.model.dto.ProductDTO;
import com.example.aloevibe.model.entity.Category;
import com.example.aloevibe.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapCategoriesToStrings")
    @Mapping(target = "id", source = "id")
    ProductDTO toDTO(Product product);

    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapStringsToCategories")
    @Mapping(target = "id", source = "id")
    Product toEntity(ProductDTO productDTO);

    @Named("mapCategoriesToStrings")
    default List<String> mapCategoriesToStrings(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    @Named("mapStringsToCategories")
    default List<Category> mapStringsToCategories(List<String> categoryNames) {
        if (categoryNames == null || categoryNames.isEmpty()) {
            return new ArrayList<>();
        }
        return categoryNames.stream()
                .map(name -> {
                    Category category = new Category();
                    category.setName(name);
                    return category;
                })
                .collect(Collectors.toList());
    }
}
