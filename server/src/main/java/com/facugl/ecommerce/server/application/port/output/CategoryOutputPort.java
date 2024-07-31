package com.facugl.ecommerce.server.application.port.output;

import java.util.List;
import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;

public interface CategoryOutputPort {    
    Category createCategory(Category categoryToCreate);
    
    Category findCategoryById(Long categoryId);
    
    Optional<Category> findCategoryByName(String categoryName);
    
    List<Category> getAllCategories();

    List<Category> getAllMainCategories();

    List<Category> getAllSubCategories(Long parentCategoryId);

    Category updateCategory(Long categoryId, Category categoryToUpdate);

    void activeCategory(Long categoryId, CategoryStatus status);
}
