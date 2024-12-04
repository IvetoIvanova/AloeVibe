package com.example.aloevibe.service;

import com.example.aloevibe.model.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(UUID id);

    ProductDTO createProduct(ProductDTO productDTO);

    void updateProduct(UUID id, ProductDTO productDTO);

    void deleteProduct(UUID id);
}
