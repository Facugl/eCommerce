package com.facugl.ecommerce.server.application.port.input.productsVariants;

import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;

public interface UpdateProductVariantUseCase {
    ProductVariant updateProductVariant(Long id, ProductVariant productVariantToUpdate);
}
