package com.example.aloevibe.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private String imageUrl;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    public Category() {
        this.products = new ArrayList<>();
    }

    public Category(UUID id, String name, String description, String imageUrl, List<Product> products) {
        super(id);
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
