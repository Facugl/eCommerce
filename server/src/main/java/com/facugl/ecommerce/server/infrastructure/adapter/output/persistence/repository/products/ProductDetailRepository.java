package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductDetailEntity;

public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
    
}
