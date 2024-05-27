package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.productsVariants.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.product JOIN FETCH pv.variantsValues WHERE pv.id = :productVariantId")
    Optional<ProductVariantEntity> findProductVariantWithVariantsValuesById(@Param("productVariantId") Long productVariantId);

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.product JOIN FETCH pv.variantsValues")
    List<ProductVariantEntity> findAllProductVariantsWithVariantsValues();

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.product p JOIN FETCH p.category c LEFT JOIN FETCH c.parentCategory WHERE p.id = :productId")
    List<ProductVariantEntity> findProductsVariantsByProduct(Long productId);

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.variantsValues vv WHERE vv.id = :variantValueId")
    List<ProductVariantEntity> findProductsVariantsByVariantValue(Long variantValueId);

}
