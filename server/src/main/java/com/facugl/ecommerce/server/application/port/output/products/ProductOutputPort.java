package com.facugl.ecommerce.server.application.port.output.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

public interface ProductOutputPort {
    Product createProduct(Product productToCreate);

    Product findProductById(Long productId);

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(Long categoryId);

    void deleteProductById(Long productId);

    Product updateProduct(Long productId, Product productToUpdate);

    void activeProduct(Long productId, ProductStatus status);
}
