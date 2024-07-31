package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;

public interface VariantValueRepository extends JpaRepository<VariantValueEntity, Long> {
    @Query("SELECT COUNT(v) = 0 FROM VariantValueEntity v WHERE LOWER(v.value) = LOWER(:value)")
    boolean isVariantValueUnique(@Param("value") String value);

    Optional<VariantValueEntity> findVariantValueByValue(String value);

    @Query("SELECT vv FROM VariantValueEntity vv JOIN FETCH vv.variant WHERE vv.id = :variantValueId")
    Optional<VariantValueEntity> findVariantValueWithVariantById(@Param("variantValueId") Long variantValueId);

    @Query("SELECT vv FROM VariantValueEntity vv JOIN FETCH vv.variant")
    List<VariantValueEntity> findAllVariantValuesWithVariant();

    @Query("SELECT vv FROM VariantValueEntity vv JOIN FETCH vv.variant v WHERE v.id = :variantId")
    List<VariantValueEntity> findVariantValuesByVariant(Long variantId);

    @Modifying
    @Transactional
    @Query("DELETE FROM VariantValueEntity vv WHERE vv.variant = :variant")
    void deleteByVariant(@Param("variant") VariantEntity variant);
}
