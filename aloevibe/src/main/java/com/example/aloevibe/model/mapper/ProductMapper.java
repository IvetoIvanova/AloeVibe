package com.example.aloevibe.model.mapper;

import com.example.aloevibe.model.dto.ProductDTO;
import com.example.aloevibe.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
