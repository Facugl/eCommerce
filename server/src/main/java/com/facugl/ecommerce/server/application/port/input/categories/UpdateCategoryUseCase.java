package com.facugl.ecommerce.server.application.port.input.categories;

import com.facugl.ecommerce.server.domain.model.categories.Category;

public interface UpdateCategoryUseCase {
    Category updateCategory(Long categoryId, Category categoryToUpdate);
}
