package com.facugl.ecommerce.server.application.port.input.products.products;

import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

public interface ActiveProductUseCase {
    void activeProduct(Long productId, ProductStatus status);
}
