package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceCategoryMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceProductMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class CategoryPersistenceAdapter implements CategoryOutputPort {

    private final CategoryRepository categoryRepository;

    private final PersistenceCategoryMapper categoryMapper;
    private final PersistenceProductMapper productMapper;

    @Override
    public boolean isCategoryNameUnique(String name) {
        return !categoryRepository.findByName(name).isPresent();
    }

    @Override
    public Category createCategory(Category category) {
        CategoryEntity categoryEntity = categoryMapper.mapToCategoryEntity(category);

        if (categoryEntity.getParentCategory() != null && categoryEntity.getParentCategory().getId() == null) {
            CategoryEntity parentCategoryEntity = categoryRepository.save(categoryEntity.getParentCategory());
            categoryEntity.setParentCategory(parentCategoryEntity);
        }

        categoryEntity.setStatus(CategoryStatus.ENABLED);

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        return categoryMapper.mapToCategory(savedCategoryEntity);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository
                .findByName(name)
                .map(categoryEntity -> categoryMapper.mapToCategory(categoryEntity));
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .map(categoryMapper::mapToCategory) // Notación más compacta
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found."));
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        List<Category> categoryList = categoryEntityList
                .stream()
                .map(categoryMapper::mapToCategory)
                .collect(Collectors.toList());

        return categoryList;

        /*
         * asi es mas compacto pero prefiero dejarlo de la otra menera
         * return
         * categoryRepository.findAll().stream().map(categoryMapper::mapToCategory).
         * collect(Collectors.toList());
         */
    }

    @Override
    public List<Category> getAllMainCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findByParentCategoryIsNull();

        List<Category> categoryList = categoryEntityList
                .stream()
                .map(categoryMapper::mapToCategory)
                .collect(Collectors.toList());

        return categoryList;
    }

    @Override
    public List<Category> getAllSubCategories(Long parentId) {
        List<CategoryEntity> categoryEntityList = categoryRepository.findByParentCategory_Id(parentId);

        List<Category> categoryList = categoryEntityList
                .stream()
                .map(categoryMapper::mapToCategory)
                .collect(Collectors.toList());

        return categoryList;
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found."));

        if (category.getName() != null) {
            categoryEntity.setName(category.getName());
        }

        // Verifica si la parentCategory existe
        if (category.getParentCategory() != null && category.getParentCategory().getId() == null) {
            // Si la parentCategory aún no ha sido persistida, se intenta persistirla
            CategoryEntity parentCategoryEntity = categoryMapper.mapToCategoryEntity(category.getParentCategory());
            categoryEntity.setParentCategory(categoryRepository.save(parentCategoryEntity));
        }

        if (category.getParentCategory() != null) {
            categoryEntity.setParentCategory(categoryMapper.mapToCategoryEntity(category.getParentCategory()));
        }

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        return categoryMapper.mapToCategory(savedCategoryEntity);
    }

    @Override
    public void activeCategory(Long id, CategoryStatus status) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found."));

        categoryEntity.setStatus(status);

        categoryRepository.save(categoryEntity);
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " not found."));

        List<Product> productList = categoryEntity
                .getProducts()
                .stream()
                .map(product -> productMapper.mapToProduct(product, categoryMapper))
                .collect(Collectors.toList());

        return productList;
    }

}
