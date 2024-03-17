package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;

@Mapper(componentModel = "spring")
public interface PersistenceCategoryMapper {

    default CategoryEntity mapToCategoryEntity(Category category) {
        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus());

        if (category.getParentCategory() != null && category.getParentCategory().getId() != null) {
            categoryEntity.parentCategory(mapToCategoryEntity(category.getParentCategory()));
        }

        return categoryEntity.build();
    }

    Category mapToCategory(CategoryEntity entity);
    
}
