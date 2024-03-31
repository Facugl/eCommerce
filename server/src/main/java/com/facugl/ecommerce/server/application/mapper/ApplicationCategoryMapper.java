package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.application.service.CategoryService;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.Category.CategoryBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.CategoryRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.CategoryResponse;

@Mapper(componentModel = "spring")
public interface ApplicationCategoryMapper {

	default Category mapCategoryRequestToCategory(CategoryRequest request, CategoryService categoryService) {
		CategoryBuilder categoryBuilder = Category.builder()
				.name(request.getName())
				.status(request.getStatus());

		if (request.getParentCategory() != null) {
			Category parentCategory = categoryService.getCategoryByName(request.getParentCategory()).orElse(null);

			categoryBuilder.parentCategory(parentCategory);
		}

		return categoryBuilder.build();
	}

	CategoryResponse mapCategoryToCategoryResponse(Category category);

}
