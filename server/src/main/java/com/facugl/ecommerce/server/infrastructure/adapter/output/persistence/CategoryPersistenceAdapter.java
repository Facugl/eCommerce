package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityNameNotUniqueException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
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
	public Category createCategory(Category category) {
		if (categoryRepository.isCategoryNameUnique(category.getName())) {
			CategoryEntity categoryEntity = categoryMapper.mapCategoryToCategoryEntity(category);

			if (categoryEntity.getParentCategory() != null && categoryEntity.getId() == null) {
				CategoryEntity parentCategoryEntity = categoryRepository
						.findByName(categoryEntity.getParentCategory().getName())
						.orElseThrow(() -> new EntityNotFoundException(
								"Category with name: " + categoryEntity.getParentCategory().getName() + " not found."));

				categoryEntity.setParentCategory(parentCategoryEntity);
			}

			categoryEntity.setStatus(CategoryStatus.ENABLED);

			CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

			return categoryMapper.mapCategoryEntityToCategory(savedCategoryEntity);
		} else {
			throw new EntityNameNotUniqueException("The category name must be unique.");
		}
	}

	@Override
	public Optional<Category> findCategoryByName(String name) {
		return categoryRepository
				.findByName(name)
				.map(categoryEntity -> categoryMapper.mapCategoryEntityToCategory(categoryEntity));
	}

	@Override
	public Category findCategoryById(Long id) {
		return categoryRepository
				.findById(id)
				.map(categoryEntity -> categoryMapper.mapCategoryEntityToCategory(categoryEntity))
				.orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found."));
	}

	@Override
	public List<Category> getAllCategories() {
		List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

		return categoryEntityList
				.stream()
				.map(categoryEntity -> categoryMapper.mapCategoryEntityToCategory(categoryEntity))
				.collect(Collectors.toList());
	}

	@Override
	public List<Category> getAllMainCategories() {
		List<CategoryEntity> categoryEntityList = categoryRepository.findByParentCategoryIsNull();

		return categoryEntityList
				.stream()
				.map(categoryEntity -> categoryMapper.mapCategoryEntityToCategory(categoryEntity))
				.collect(Collectors.toList());
	}

	@Override
	public List<Category> getAllSubCategories(Long parentId) {
		List<CategoryEntity> categoryEntityList = categoryRepository.findByParentCategory_Id(parentId);

		return categoryEntityList
				.stream()
				.map(categoryEntity -> categoryMapper.mapCategoryEntityToCategory(categoryEntity))
				.collect(Collectors.toList());
	}

	@Override
	public Category updateCategory(Long id, Category category) {
		CategoryEntity categoryEntity = categoryRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found."));

		if (category.getName() != null) {
			categoryEntity.setName(category.getName());
		}

		if (category.getParentCategory() != null) {
			CategoryEntity parentCategory = categoryRepository
					.findByName(category.getParentCategory().getName())
					.orElseThrow(() -> new EntityNotFoundException(
							"Category with name: " + category.getParentCategory().getName() + " not found."));

			categoryEntity.setParentCategory(parentCategory);
		}

		CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);

		return categoryMapper.mapCategoryEntityToCategory(savedCategoryEntity);
	}

	@Override
	public void activeCategory(Long id, CategoryStatus status) {
		CategoryEntity categoryEntity = categoryRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found."));

		categoryEntity.setStatus(status);

		categoryRepository.save(categoryEntity);
	}

}
