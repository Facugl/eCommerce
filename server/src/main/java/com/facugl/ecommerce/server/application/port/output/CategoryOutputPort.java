package com.facugl.ecommerce.server.application.port.output;

import java.util.List;
import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;

public interface CategoryOutputPort {
    
    Category createCategory(Category category);
    
    Category findCategoryById(Long id);
    
    Optional<Category> findCategoryByName(String name);
    
    List<Category> getAllCategories();

    List<Category> getAllMainCategories();

    List<Category> getAllSubCategories(Long parentId);

    Category updateCategory(Long id, Category category);

    void activeCategory(Long id, CategoryStatus status);

}
