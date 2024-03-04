package com.facugl.ecommerce.server.application.port.input.products;

import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

public interface ActiveProductUseCase {
    void activeProduct(Long id, ProductStatus status);
}
