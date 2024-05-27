package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.category c LEFT JOIN FETCH c.parentCategory WHERE p.id = :productId")
    Optional<ProductEntity> findProductWithCategoryById(@Param("productId") Long productId);

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.category c LEFT JOIN FETCH c.parentCategory")
    List<ProductEntity> findAllProductsWithCategory();

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.category c LEFT JOIN FETCH c.parentCategory WHERE c.id = :categoryId")
    List<ProductEntity> findProductsByCategoryId(@Param("categoryId") Long categoryId);

}
