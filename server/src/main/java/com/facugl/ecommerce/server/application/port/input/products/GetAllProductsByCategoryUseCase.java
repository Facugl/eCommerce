package com.facugl.ecommerce.server.application.port.input.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.Product;

public interface GetAllProductsByCategoryUseCase {
    List<Product> getAllProductsByCategory(Long categoryId);
}
