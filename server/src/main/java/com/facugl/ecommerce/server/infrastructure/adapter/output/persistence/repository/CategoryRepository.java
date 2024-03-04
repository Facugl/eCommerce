package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT COUNT(c) = 0 FROM CategoryEntity c WHERE LOWER(c.name) = LOWER(:name)")
    boolean isCategoryNameUnique(@Param("name") String name);

    Optional<CategoryEntity> findByName(String name);

    List<CategoryEntity> findByStatusTrue();

    List<CategoryEntity> findByParentCategoryIsNull();

    List<CategoryEntity> findByParentCategory_Id(Long parentId);

}
