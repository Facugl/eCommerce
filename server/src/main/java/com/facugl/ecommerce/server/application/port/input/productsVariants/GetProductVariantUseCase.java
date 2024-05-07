package com.facugl.ecommerce.server.application.port.input.productsVariants;

import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;

public interface GetProductVariantUseCase {
    ProductVariant getProductVariantById(Long id);
}
