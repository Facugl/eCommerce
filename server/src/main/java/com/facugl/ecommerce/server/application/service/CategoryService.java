package com.facugl.ecommerce.server.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.categories.ActiveCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.CreateCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllMainCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllSubCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.UpdateCategoryUseCase;
import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.Category.CategoryBuilder;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.CategoryRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class CategoryService implements
        CreateCategoryUseCase,
        GetCategoryUseCase,
        GetAllCategoriesUseCase,
        GetAllMainCategoriesUseCase,
        GetAllSubCategoriesUseCase,
        UpdateCategoryUseCase,
        ActiveCategoryUseCase {
    private final CategoryOutputPort categoryOutputPort;

    @Transactional
    @Override
    public Category createCategory(Category category) {
        category.setStatus(CategoryStatus.ENABLED);
        
        return categoryOutputPort.createCategory(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(Long id) {
        return categoryOutputPort.findCategoryById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryOutputPort.findCategoryByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories() {
        return categoryOutputPort.getAllCategories();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllMainCategories() {
        return categoryOutputPort.getAllMainCategories();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllSubCategories(Long parentId) {
        return categoryOutputPort.getAllSubCategories(parentId);
    }

    @Transactional
    @Override
    public Category updateCategory(Long id, Category categoryToUpdate) {
        return categoryOutputPort.updateCategory(id, categoryToUpdate);
    }

    @Transactional
    @Override
    public void activeCategory(Long id, CategoryStatus status) {
        if (status == CategoryStatus.ENABLED || status == CategoryStatus.DISABLED) {
            categoryOutputPort.activeCategory(id, status);
        }
    }

    @Transactional
    public Category mapCategoryRequestToCategory(CategoryRequest categoryToCreate) {
        CategoryBuilder category = Category.builder()
                .name(categoryToCreate.getName());

        if (categoryToCreate.getParentCategoryId() != null) {
            Category parentCategory = categoryOutputPort.findCategoryById(categoryToCreate.getParentCategoryId());

            category.parentCategory(parentCategory);
        }

        return category.build();
    }
}
