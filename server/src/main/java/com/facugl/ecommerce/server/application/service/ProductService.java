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
import com.facugl.ecommerce.server.common.exception.generic.EntityNameNotUniqueException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
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
        if (productOutputPort.isProductNameUnique(product.getName())) {
            return productOutputPort.createProduct(product);
        } else {
            throw new EntityNameNotUniqueException("Product with name: " + product.getName() + " already exist.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProductById(Long id) {
        return productOutputPort.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getAllProducts() {
        return productOutputPort.getAllProducts();
    }

    @Transactional
    @Override
    public void deleteProductById(Long id) {
        Product product = productOutputPort.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        productOutputPort.deleteProductById(product.getId());
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, Product productToUpdate) {
        if (productOutputPort.isProductNameUnique(productToUpdate.getName())) {
            return productOutputPort.updateProduct(id, productToUpdate);
        } else {
            throw new EntityNameNotUniqueException(
                    "Product with name: " + productToUpdate.getName() + " already exist.");
        }
    }

    @Transactional
    @Override
    public void activeProduct(Long id, ProductStatus status) {
        productOutputPort.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        if (status == ProductStatus.ENABLED || status == ProductStatus.DISABLED) {
            productOutputPort.activeProduct(id, status);
        }
    }

}
