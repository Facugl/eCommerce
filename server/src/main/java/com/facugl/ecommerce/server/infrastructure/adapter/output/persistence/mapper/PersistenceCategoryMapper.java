package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;

@Mapper(componentModel = "spring")
public interface PersistenceCategoryMapper {

	@Mapping(target = "id", ignore = true)
	CategoryEntity mapCategoryToCategoryEntity(Category category);

	Category mapCategoryEntityToCategory(CategoryEntity categoryEntity);

}
