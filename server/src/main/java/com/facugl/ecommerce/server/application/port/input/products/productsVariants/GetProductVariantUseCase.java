package com.facugl.ecommerce.server.application.port.input.products.productsVariants;

import com.facugl.ecommerce.server.domain.model.products.ProductVariant;

public interface GetProductVariantUseCase {
    ProductVariant getProductVariantById(Long productVariantId);
}
