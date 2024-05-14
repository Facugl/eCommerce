package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.category c WHERE c.id = :categoryId")
    List<ProductEntity> findProductsByCategoryId(@Param("categoryId") Long categoryId);

}
