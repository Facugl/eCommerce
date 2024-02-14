package com.facugl.ecommerce.server.application.port.input.categories;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.Category;

public interface GetAllSubCategoriesUseCase {
    List<Category> getAllSubCategories(Long parentId);
}
