package com.example.aloevibe.service.impl;

import com.example.aloevibe.model.dto.ProductDTO;
import com.example.aloevibe.model.entity.Category;
import com.example.aloevibe.model.entity.Product;
import com.example.aloevibe.model.mapper.ProductMapper;
import com.example.aloevibe.repository.CategoryRepository;
import com.example.aloevibe.repository.ProductRepository;
import com.example.aloevibe.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public void updateProduct(UUID id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setImages(productDTO.getImages());
        product.setPrice(productDTO.getPrice());

        if (productDTO.getCategories() != null) {
            List<Category> categories = productDTO.getCategories().stream()
                    .map(categoryName -> categoryRepository.findByName(categoryName)
                            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryName)))
                    .collect(Collectors.toList());

            product.setCategories(categories);
        }

        if (productDTO.getCertificates() != null) {
            product.setCertificates(new ArrayList<>(productDTO.getCertificates()));
        }

        if (productDTO.getIngredients() != null) {
            product.setIngredients(new ArrayList<>(productDTO.getIngredients()));
        }

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

}
