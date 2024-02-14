package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.common.exception.generic.SelfParentCategoryException;
import com.facugl.ecommerce.server.domain.model.Category;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    default CategoryEntity mapToCategoryEntity(Category category, CategoryRepository categoryRepository) {

        CategoryEntity.CategoryEntityBuilder builder = CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.isStatus());

        if (category.getParentCategory() != null && category.getParentCategory().getId() != null) {
            builder.parentCategory(mapToCategoryEntity(category.getParentCategory(), categoryRepository));
        }
        
        if (category.getParentCategory() != null && category.getParentCategory().getId() == null) {
            Optional<CategoryEntity> parentCategoryOptional = categoryRepository
                    .findByName(category.getParentCategory().getName());

            parentCategoryOptional.ifPresent(parentCategory -> {
                if (!parentCategory.getId().equals(category.getId())) {
                    builder.parentCategory((parentCategory));
                } else {
                    throw new SelfParentCategoryException("A category cannot be its own parentCategory.");
                }
            });
        }

        return builder.build();
    }

    Category mapToCategory(CategoryEntity entity);
    
}
