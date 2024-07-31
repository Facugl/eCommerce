package com.facugl.ecommerce.server.application.service.products;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.products.productsVariants.CreateProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.DeleteProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.GetAllProductsVariantsByProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.GetAllProductsVariantsByVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.GetAllProductsVariantsUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.GetProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.UpdateProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.output.products.ProductOutputPort;
import com.facugl.ecommerce.server.application.port.output.products.ProductVariantOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductVariant;
import com.facugl.ecommerce.server.domain.model.products.ProductVariant.ProductVariantBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductVariantRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class ProductVariantService implements
        CreateProductVariantUseCase,
        GetProductVariantUseCase,
        GetAllProductsVariantsUseCase,
        GetAllProductsVariantsByProductUseCase,
        GetAllProductsVariantsByVariantValueUseCase,
        UpdateProductVariantUseCase,
        DeleteProductVariantUseCase {
    private final ProductVariantOutputPort productVariantOutputPort;
    private final ProductOutputPort productOutputPort;

    @Transactional
    @Override
    public ProductVariant createProductVariant(ProductVariant productVariantToCreate) {
        return productVariantOutputPort.createProductVariant(productVariantToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductVariant getProductVariantById(Long productVariantId) {
        return productVariantOutputPort.findProductVariantById(productVariantId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductVariant> getAllProductsVariants() {
        return productVariantOutputPort.getAllProductsVariants();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductVariant> getAllProductsVariantsByProduct(Long productId) {
        return productVariantOutputPort.getAllProductsVariantsByProduct(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductVariant> getAllProductsVariantsByVariantValue(Long variantValueId) {
        return productVariantOutputPort.getAllProductsVariantsByVariantValue(variantValueId);
    }

    @Transactional
    @Override
    public ProductVariant updateProductVariant(Long productVariantId, ProductVariant productVariantToUpdate) {
        return productVariantOutputPort.updateProductVariant(productVariantId, productVariantToUpdate);
    }

    @Transactional
    @Override
    public void deleteProductVariantById(Long productVariantId) {
        productVariantOutputPort.deleteProductVariantById(productVariantId);
    }

    @Transactional
    public ProductVariant mapProductVariantRequestToProductVariant(ProductVariantRequest productVariantToCreate) {
        ProductVariantBuilder productVariantBuilder = ProductVariant.builder()
                .description(productVariantToCreate.getDescription())
                .images(productVariantToCreate.getImages())
                .price(productVariantToCreate.getPrice())
                .sku(productVariantToCreate.getSku())
                .stock(productVariantToCreate.getStock());

        if (productVariantToCreate.getProductId() != null) {
            Product product = productOutputPort.findProductById(productVariantToCreate.getProductId());
            productVariantBuilder.product(product);
        }

        return productVariantBuilder.build();
    }
}
