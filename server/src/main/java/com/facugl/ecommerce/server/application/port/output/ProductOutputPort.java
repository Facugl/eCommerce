package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

public interface ProductOutputPort {

    Product createProduct(Product product);

    Product findProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(Long categoryId);

    void deleteProductById(Long id);

    Product updateProduct(Long id, Product productToUpdate);

    void activeProduct(Long id, ProductStatus status);

}
