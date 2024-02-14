package com.facugl.ecommerce.server.application.port.input.categories;

import com.facugl.ecommerce.server.domain.model.Category;

public interface CreateCategoryUseCase {
    Category createCategory(Category category);
}
