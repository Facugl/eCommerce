package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;

public interface VariantRepository extends JpaRepository<VariantEntity, Long> {

    Optional<VariantEntity> findByName(String name);

    @Query("SELECT v FROM VariantEntity v WHERE v.category.id = :categoryId")
    List<VariantEntity> findByCategoryId(@Param("categoryId") Long categoryId);

    // @Query("SELECT v FROM VariantEntity v WHERE v.categoryId = :categoryId AND v.name = :variantName")
    // List<VariantEntity> findByCategoryIdAndName(@Param("categoryId") Long categoryId,
    //         @Param("variantName") String variantName);

}
