package com.facugl.ecommerce.server.application.port.input.categories;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.categories.Category;

public interface GetAllMainCategoriesUseCase {
    List<Category> getAllMainCategories();
}
