package com.facugl.ecommerce.server.application.port.input.productsVariants;

import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;

public interface CreateProductVariantUseCase {
    ProductVariant createProductVariant(ProductVariant productVariantToCreate);
}
