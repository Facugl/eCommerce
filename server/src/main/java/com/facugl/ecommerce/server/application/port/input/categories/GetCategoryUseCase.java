package com.facugl.ecommerce.server.application.port.input.categories;

import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.categories.Category;

public interface GetCategoryUseCase {

    Category getCategoryById(Long id);

    Optional<Category> getCategoryByName(String name);

}
