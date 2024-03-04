package com.facugl.ecommerce.server.application.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.CategoryRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.CategoryResponse;

@Mapper(componentModel = "spring", uses = CategoryOutputPort.class)
public interface ApplicationCategoryMapper {

    default Category mapToCategory(CategoryRequest request, CategoryOutputPort categoryOutputPort) {

        if (request.getParentCategory() != null) {
            Optional<Category> categoryOptional = categoryOutputPort.findCategoryByName(request.getParentCategory());

            if (categoryOptional.isEmpty()) {
                Category newParentCategory = categoryOutputPort.createCategory(
                        Category.builder()
                                .name(request.getParentCategory())
                                .status(CategoryStatus.ENABLED)
                                .parentCategory(null)
                                .build());

                return Category.builder()
                        .name(request.getName())
                        .status(request.getStatus())
                        .parentCategory(newParentCategory)
                        .build();
            }

            return Category.builder()
                    .name(request.getName())
                    .status(request.getStatus())
                    .parentCategory(categoryOptional.get())
                    .build();

        }

        return Category.builder()
                .name(request.getName())
                .status(request.getStatus())
                .parentCategory(null)
                .build();

    }

    default CategoryResponse mapToCategoryResponse(Category category) {
        if (category.getParentCategory() == null) {
            return CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .status(category.getStatus())
                    .parentCategory(null)
                    .build();
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .parentCategory(category.getParentCategory())
                .build();
    }

}
