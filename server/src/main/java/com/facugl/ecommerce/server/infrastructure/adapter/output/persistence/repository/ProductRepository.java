package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT COUNT(p) = 0 FROM ProductEntity p WHERE LOWER(p.name) = LOWER(:name)")
    boolean isCategoryNameUnique(@Param("name") String name);

    Optional<ProductEntity> findByName(String name);

}
