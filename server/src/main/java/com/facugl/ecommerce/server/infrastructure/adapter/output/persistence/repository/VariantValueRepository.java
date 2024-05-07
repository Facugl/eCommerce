package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;

public interface VariantValueRepository extends JpaRepository<VariantValueEntity, Long> {

    @Query("SELECT COUNT(v) = 0 FROM VariantValueEntity v WHERE LOWER(v.value) = LOWER(:value)")
    boolean isVariantValueUnique(@Param("value") String value);

    Optional<VariantValueEntity> findVariantValueByValue(String value);

    // @Query("SELECT v FROM VariantValueEntity v WHERE v.variant.name = :variantName AND v.value = :value")
    // List<VariantValueEntity> findyByVariantNameAndValue(@Param("variantName") String variantName,
    //         @Param("value") String value);

}
