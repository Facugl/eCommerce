package com.facugl.ecommerce.server.application.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.products.ActiveProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.CreateProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.DeleteProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.GetAllProductsByCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.products.GetAllProductsUseCase;
import com.facugl.ecommerce.server.application.port.input.products.GetProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.UpdateProductUseCase;
import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.Product.ProductBuilder;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class ProductService implements
        CreateProductUseCase,
        GetProductUseCase,
        GetAllProductsUseCase,
        GetAllProductsByCategoryUseCase,
        DeleteProductUseCase,
        UpdateProductUseCase,
        ActiveProductUseCase {
    private final ProductOutputPort productOutputPort;
    private final CategoryOutputPort categoryOutputPort;

    @Transactional
    @Override
    public Product createProduct(Product productToCreate) {
        productToCreate.setStatus(ProductStatus.ENABLED);

        return productOutputPort.createProduct(productToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProductById(Long productId) {
        return productOutputPort.findProductById(productId);
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
    public void deleteProductById(Long productId) {
        productOutputPort.deleteProductById(productId);
    }

    @Transactional
    @Override
    public Product updateProduct(Long productId, Product productToUpdate) {
        return productOutputPort.updateProduct(productId, productToUpdate);
    }

    @Transactional
    @Override
    public void activeProduct(Long productId, ProductStatus status) {
        if (status == ProductStatus.ENABLED || status == ProductStatus.DISABLED) {
            productOutputPort.activeProduct(productId, status);
        }
    }

    @Transactional
    public Product mapProductRequestToProduct(ProductRequest productToCreate) {
        ProductBuilder productBuilder = Product.builder()
                .name(productToCreate.getName())
                .description(productToCreate.getDescription())
                .images(productToCreate.getImages())
                .status(productToCreate.getStatus());

        if (productToCreate.getCategoryId() != null) {
            Category category = categoryOutputPort.findCategoryById(productToCreate.getCategoryId());

            productBuilder.category(category);
        }

        return productBuilder.build();
    }
}
