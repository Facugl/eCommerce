package com.facugl.ecommerce.server.application.port.input.products.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.Product;

public interface GetAllProductsUseCase {
    List<Product> getAllProducts();
}
