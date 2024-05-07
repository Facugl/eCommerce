package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.productsVariants.ProductVariantEntity;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {

}
