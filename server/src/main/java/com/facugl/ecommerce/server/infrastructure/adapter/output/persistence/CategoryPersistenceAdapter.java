package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.Category;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.CategoryEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class CategoryPersistenceAdapter implements CategoryOutputPort {

    private final CategoryRepository categoryRepository;
    private final PersistenceMapper categoryMapper;

    @Override
    public boolean isCategoryNameUnique(String name) {
        return !categoryRepository.findByName(name).isPresent();
    }

    @Override
    public Category createCategory(Category category) {
        CategoryEntity categoryEntity = categoryMapper.mapToCategoryEntity(category, categoryRepository);

        if (categoryEntity.getParentCategory() != null && categoryEntity.getParentCategory().getId() == null) {
            // Si la parentCategory aún no ha sido persistida, se intenta persistirla
            Category parentCategory = categoryMapper.mapToCategory(categoryEntity.getParentCategory());
            CategoryEntity parentCategoryEntity = categoryMapper.mapToCategoryEntity(parentCategory,
                    categoryRepository);
            categoryEntity.setParentCategory(categoryRepository.save(parentCategoryEntity));
        }

        categoryEntity.setStatus(true);
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        return categoryMapper.mapToCategory(savedCategoryEntity);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::mapToCategory);
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::mapToCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        List<Category> categories = categoryEntities
                .stream()
                .map(categoryMapper::mapToCategory)
                .collect(Collectors.toList());

        return categories;

        /*
         * asi es mas compacto pero prefiero dejarlo de la otra menera
         * return
         * categoryRepository.findAll().stream().map(categoryMapper::mapToCategory).
         * collect(Collectors.toList());
         */
    }

    @Override
    public List<Category> getAllMainCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findByParentCategoryIsNull();

        List<Category> categories = categoryEntities
                .stream()
                .map(categoryMapper::mapToCategory)
                .collect(Collectors.toList());

        return categories;
    }

    @Override
    public List<Category> getAllSubCategories(Long parentId) {
        List<CategoryEntity> categoryEntities = categoryRepository.findByParentCategory_Id(parentId);

        List<Category> categories = categoryEntities
                .stream()
                .map(categoryMapper::mapToCategory)
                .collect(Collectors.toList());

        return categories;
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(id)
                .map(categoryMapper::mapToCategory);

        CategoryEntity categoryEntity = categoryMapper.mapToCategoryEntity(categoryOptional.get(), categoryRepository);

        if (categoryOptional.isPresent()) {
            categoryEntity.setName(category.getName());

            // Verifica si la parentCategory existe
            if (category.getParentCategory() != null && category.getParentCategory().getId() == null) {
                // Si la parentCategory aún no ha sido persistida, se intenta persistirla
                CategoryEntity parentCategoryEntity = categoryMapper.mapToCategoryEntity(category.getParentCategory(),
                        categoryRepository);
                categoryEntity.setParentCategory(categoryRepository.save(parentCategoryEntity));
            }

            if (category.getParentCategory() != null ) {
                categoryEntity.setParentCategory(categoryMapper.mapToCategoryEntity(category.getParentCategory(), categoryRepository));
            }
        }

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

        return categoryMapper.mapToCategory(savedCategoryEntity);
    }

    @Override
    public Category activeCategogry(Long id, Category category) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);

        CategoryEntity categoryEntity = categoryEntityOptional.get();
        categoryEntity.setStatus(category.isStatus());

        return categoryMapper.mapToCategory(categoryRepository.save(categoryEntity));
    }

}
