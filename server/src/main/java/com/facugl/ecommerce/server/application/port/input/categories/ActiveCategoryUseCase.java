package com.facugl.ecommerce.server.application.port.input.categories;

import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;

public interface ActiveCategoryUseCase {
    void activeCategory(Long id, CategoryStatus status);
}
