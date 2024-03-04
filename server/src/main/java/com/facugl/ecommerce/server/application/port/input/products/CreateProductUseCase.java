package com.facugl.ecommerce.server.application.port.input.products;

import com.facugl.ecommerce.server.domain.model.products.Product;

public interface CreateProductUseCase {
    Product createProduct(Product product);
}
