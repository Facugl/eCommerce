package com.facugl.ecommerce.server.application.port.input.products.productsVariants;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.ProductVariant;

public interface GetAllProductsVariantsUseCase {
    List<ProductVariant> getAllProductsVariants();
}
