package com.facugl.ecommerce.server.application.port.input.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;

public interface GetAllProductsVariantsByProductUseCase {

    List<ProductVariant> getAllProductsVariantsByProduct(Long productId);

}
