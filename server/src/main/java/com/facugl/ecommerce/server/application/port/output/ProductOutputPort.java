package com.facugl.ecommerce.server.application.port.output;

import java.util.List;
import java.util.Optional;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

public interface ProductOutputPort {

    boolean isProductNameUnique(String name);

    Product createProduct(Product product);

    Optional<Product> findProductById(Long id);

    List<Product> getAllProducts();

    void deleteProductById(Long id);

    Product updateProduct(Long id, Product productToUpdate);

    void activeProduct(Long id, ProductStatus status);

}
