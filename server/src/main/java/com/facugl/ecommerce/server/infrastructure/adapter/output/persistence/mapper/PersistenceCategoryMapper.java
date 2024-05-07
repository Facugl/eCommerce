package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;

@Mapper(componentModel = "spring")
public interface PersistenceCategoryMapper {

	@Mapping(target = "id", ignore = true)
	CategoryEntity mapCategoryToCategoryEntity(Category category,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@InheritInverseConfiguration
	Category mapCategoryEntityToCategory(CategoryEntity entity,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@DoIgnore
	default CategoryEntity mapCategoryToCategoryEntity(Category category) {
		return mapCategoryToCategoryEntity(category, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default Category mapCategoryEntityToCategory(CategoryEntity entity) {
		return mapCategoryEntityToCategory(entity, new CycleAvoidingMappingContext());
	}

}
