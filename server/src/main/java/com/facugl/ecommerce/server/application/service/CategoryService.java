package com.facugl.ecommerce.server.application.service;

import java.util.List;

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
import com.facugl.ecommerce.server.common.exception.generic.CategoryNameNotUniqueException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.Category;

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

    public CategoryService(CategoryOutputPort categoryOutputPort) {
        this.categoryOutputPort = categoryOutputPort;
    }

    @Transactional
    @Override
    public Category createCategory(Category category) {
        if (categoryOutputPort.isCategoryNameUnique(category.getName())) {
            return categoryOutputPort.createCategory(category);
        } else {
            throw new CategoryNameNotUniqueException("The category name must be unique.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(Long id) {
        return categoryOutputPort.findCategoryById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found."));
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
        if (categoryOutputPort.isCategoryNameUnique(categoryToUpdate.getName())) {
            return categoryOutputPort.updateCategory(id, categoryToUpdate);
        } else {
            throw new CategoryNameNotUniqueException("The category name must be unique.");
        }
    }

    @Transactional
    @Override
    public Category activeCategory(Long id, Category category) {
        return categoryOutputPort.activeCategogry(id, category);
    }

}
