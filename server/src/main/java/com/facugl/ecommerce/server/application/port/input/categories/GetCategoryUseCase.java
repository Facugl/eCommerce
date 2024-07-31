package com.facugl.ecommerce.server.application.port.input.categories;

import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.categories.Category;

public interface GetCategoryUseCase {
    Category getCategoryById(Long CategoryId);

    Optional<Category> getCategoryByName(String categoryName);
}
