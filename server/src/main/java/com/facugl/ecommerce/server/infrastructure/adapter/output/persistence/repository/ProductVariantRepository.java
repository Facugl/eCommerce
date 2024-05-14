package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.productsVariants.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.product p WHERE p.id = :productId")
    List<ProductVariantEntity> findProductsVariantsByProduct(Long productId);

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.variantsValues vv WHERE vv.id = :variantValueId")
    List<ProductVariantEntity> findProductsVariantsByVariantValue(Long variantValueId);

}
