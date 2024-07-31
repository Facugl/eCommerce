package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.products;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.product WHERE pv.id = :productVariantId")
    Optional<ProductVariantEntity> findProductVariantById(@Param("productVariantId") Long productVariantId);

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.product")
    List<ProductVariantEntity> findAllProductVariants();

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.product p WHERE p.id = :productId")
    List<ProductVariantEntity> findProductsVariantsByProduct(@Param("productId") Long productId);

    @Query("SELECT pv FROM ProductVariantEntity pv JOIN FETCH pv.productsDetails pd WHERE pd.variantValue.id = :variantValueId")
    List<ProductVariantEntity> findProductsVariantsByVariantValue(@Param("variantValueId") Long variantValueId);
}
