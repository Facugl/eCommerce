package com.facugl.ecommerce.server.application.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.products.ActiveProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.CreateProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.DeleteProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.GetAllProductsUseCase;
import com.facugl.ecommerce.server.application.port.input.products.GetProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.UpdateProductUseCase;
import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class ProductService implements
        CreateProductUseCase,
        GetProductUseCase,
        GetAllProductsUseCase,
        DeleteProductUseCase,
        UpdateProductUseCase,
        ActiveProductUseCase {

    private final ProductOutputPort productOutputPort;

    @Transactional
    @Override
    public Product createProduct(Product product) {
        return productOutputPort.createProduct(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProductById(Long id) {
        return productOutputPort.findProductById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getAllProducts() {
        return productOutputPort.getAllProducts();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        return productOutputPort.getAllProductsByCategory(categoryId);
    }

    @Transactional
    @Override
    public void deleteProductById(Long id) {
        productOutputPort.deleteProductById(id);
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, Product productToUpdate) {
        return productOutputPort.updateProduct(id, productToUpdate);
    }

    @Transactional
    @Override
    public void activeProduct(Long id, ProductStatus status) {
        if (status == ProductStatus.ENABLED || status == ProductStatus.DISABLED) {
            productOutputPort.activeProduct(id, status);
        }
    }

}
