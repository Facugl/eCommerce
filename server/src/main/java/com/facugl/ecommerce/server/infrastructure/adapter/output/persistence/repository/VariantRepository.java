package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;

public interface VariantRepository extends JpaRepository<VariantEntity, Long> {
    @Query("SELECT COUNT(v) = 0 FROM VariantEntity v WHERE LOWER(v.name) = LOWER(:variantName)")
    boolean isVariantNameUnique(@Param("variantName") String variantName);

    Optional<VariantEntity> findByName(String variantName);
}
