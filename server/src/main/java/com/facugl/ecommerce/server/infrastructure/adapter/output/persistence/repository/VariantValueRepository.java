package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;

public interface VariantValueRepository extends JpaRepository<VariantValueEntity, Long> {

    @Query("SELECT COUNT(v) = 0 FROM VariantValueEntity v WHERE LOWER(v.value) = LOWER(:value)")
    boolean isVariantValueUnique(@Param("value") String value);

    Optional<VariantValueEntity> findVariantValueByValue(String value);

    @Query("SELECT vv FROM VariantValueEntity vv JOIN FETCH vv.variant v WHERE v.id = :variantId")
    List<VariantValueEntity> findVariantValuesByVariant(Long variantId);

    @Query("SELECT vv FROM VariantValueEntity vv JOIN FETCH vv.productsVariants pv WHERE pv.id = :productVariantId")
    List<VariantValueEntity> findVariantValuesByProductVariant(Long productVariantId);

}
