package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query("SELECT COUNT(c) = 0 FROM CategoryEntity c WHERE LOWER(c.name) = LOWER(:categoryName)")
    boolean isCategoryNameUnique(@Param("categoryName") String categoryName);

    @Query("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.parentCategory pc WHERE c.id = :categoryId")
    Optional<CategoryEntity> findCategoryWithParentCategoryById(@Param("categoryId") Long categoryId);

    @Query("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.parentCategory pc")
    List<CategoryEntity> findAllCategoriesWithParentCategory();

    Optional<CategoryEntity> findByName(String categoryName);

    List<CategoryEntity> findByStatusTrue();

    List<CategoryEntity> findByParentCategoryIsNull();

    @Query("SELECT c FROM CategoryEntity c JOIN FETCH c.parentCategory pc WHERE pc.id = :parentCategoryId")
    List<CategoryEntity> findAllSubCategoriesByParentCategory(@Param("parentCategoryId") Long parentCategoryId);
}
