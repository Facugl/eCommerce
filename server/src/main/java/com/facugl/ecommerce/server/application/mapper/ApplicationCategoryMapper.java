package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.CategoryResponse;

@Mapper(componentModel = "spring")
public interface ApplicationCategoryMapper {

	CategoryResponse mapCategoryToCategoryResponse(Category category);

}
