package com.facugl.ecommerce.server.application.port.output;

import java.util.List;
import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.domain.model.products.Product;

public interface CategoryOutputPort {
    
    boolean isCategoryNameUnique(String name);
    
    Optional<Category> findCategoryById(Long id);
    
    Optional<Category> findCategoryByName(String name);
    
    List<Category> getAllCategories();
    
    Category createCategory(Category category);

    List<Category> getAllMainCategories();

    List<Category> getAllSubCategories(Long parentId);

    Category updateCategory(Long id, Category category);

    void activeCategory(Long id, CategoryStatus status);

    List<Product> getAllProductsByCategory(Long categoryId);

}
