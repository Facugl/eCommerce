package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :categoryId")
    List<ProductEntity> findByCategoryId(@Param("categoryId") Long categoryId);

    // @Query("SELECT p FROM ProductEntity p WHERE p.categoryId = :categoryId AND p.name = :name")
    // List<ProductEntity> findByCategoryIdAndName(@Param("categoryId") Long categoryId,
    //         @Param("name") String name);

}
