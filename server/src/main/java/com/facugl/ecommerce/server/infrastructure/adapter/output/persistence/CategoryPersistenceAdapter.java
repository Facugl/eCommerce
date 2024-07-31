package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNameNotUniqueException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceCategoryMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class CategoryPersistenceAdapter implements CategoryOutputPort {
	private final CategoryRepository categoryRepository;
	private final PersistenceCategoryMapper categoryMapper;

	@Override
	public Category createCategory(Category categoryToCreate) {
		if (categoryRepository.isCategoryNameUnique(categoryToCreate.getName())) {
			CategoryEntity categoryEntity = categoryMapper.mapCategoryToCategoryEntity(categoryToCreate);

			if (categoryEntity.getParentCategory() != null && categoryEntity.getId() == null) {
				CategoryEntity parentCategoryEntity = categoryRepository
						.findByName(categoryEntity.getParentCategory().getName())
						.orElseThrow(() -> new EntityNotFoundException(
								"Category with name: " + categoryEntity.getParentCategory().getName() + " not found."));

				categoryEntity.setParentCategory(parentCategoryEntity);
			}

			CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

			return categoryMapper.mapCategoryEntityToCategory(savedCategoryEntity);
		} else {
			throw new EntityNameNotUniqueException("The category name must be unique.");
		}
	}

	@Override
	public Optional<Category> findCategoryByName(String categoryName) {
		return categoryRepository
				.findByName(categoryName)
				.map(categoryMapper::mapCategoryEntityToCategory);
	}

	@Override
	public Category findCategoryById(Long categoryId) {
		return categoryRepository
				.findCategoryWithParentCategoryById(categoryId)
				.map(categoryMapper::mapCategoryEntityToCategory)
				.orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " not found."));
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository
				.findAllCategoriesWithParentCategory()
				.stream()
				.map(categoryMapper::mapCategoryEntityToCategory)
				.collect(Collectors.toList());
	}

	@Override
	public List<Category> getAllMainCategories() {
		return categoryRepository
				.findByParentCategoryIsNull()
				.stream()
				.map(categoryMapper::mapCategoryEntityToCategory)
				.collect(Collectors.toList());
	}

	@Override
	public List<Category> getAllSubCategories(Long parentCategoryId) {
		return categoryRepository
				.findAllSubCategoriesByParentCategory(parentCategoryId)
				.stream()
				.map(categoryMapper::mapCategoryEntityToCategory)
				.collect(Collectors.toList());
	}

	@Override
	public Category updateCategory(Long parentCategoryId, Category categoryToUpdate) {
		CategoryEntity categoryEntity = categoryRepository
				.findCategoryWithParentCategoryById(parentCategoryId)
				.orElseThrow(
						() -> new EntityNotFoundException("Category with id: " + parentCategoryId + " not found."));

		if (categoryToUpdate.getName() != null) {
			categoryEntity.setName(categoryToUpdate.getName());
		}

		if (categoryToUpdate.getParentCategory() != null) {
			CategoryEntity parentCategoryEntity = categoryRepository
					.findByName(categoryToUpdate.getParentCategory().getName())
					.orElseThrow(() -> new EntityNotFoundException(
							"Category with name: " + categoryToUpdate.getParentCategory().getName() + " not found."));

			categoryEntity.setParentCategory(parentCategoryEntity);
		}

		CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

		return categoryMapper.mapCategoryEntityToCategory(savedCategoryEntity);
	}

	@Override
	public void activeCategory(Long categoryId, CategoryStatus status) {
		CategoryEntity categoryEntity = categoryRepository
				.findCategoryWithParentCategoryById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " not found."));

		categoryEntity.setStatus(status);

		categoryRepository.save(categoryEntity);
	}
}
