package com.facugl.ecommerce.server.application.port.input.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.Product;

public interface GetAllProductsUseCase {

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(Long categoryId);

}
