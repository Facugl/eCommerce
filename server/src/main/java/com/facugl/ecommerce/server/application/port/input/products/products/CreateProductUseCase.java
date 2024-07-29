package com.facugl.ecommerce.server.application.port.input.products.products;

import com.facugl.ecommerce.server.domain.model.products.Product;

public interface CreateProductUseCase {
    Product createProduct(Product productToCreate);
}
