package com.facugl.ecommerce.server.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.productsVariants.CreateProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.productsVariants.DeleteProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.productsVariants.GetAllProductVariantsUseCase;
import com.facugl.ecommerce.server.application.port.input.productsVariants.GetProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.productsVariants.UpdateProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.application.port.output.ProductVariantOutputPort;
import com.facugl.ecommerce.server.application.port.output.VariantValueOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;
import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant.ProductVariantBuilder;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductVariantRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class ProductVariantService implements
        CreateProductVariantUseCase,
        GetProductVariantUseCase,
        GetAllProductVariantsUseCase,
        UpdateProductVariantUseCase,
        DeleteProductVariantUseCase {

    private final ProductVariantOutputPort productVariantOutputPort;
    private final ProductOutputPort productOutputPort;
    private final VariantValueOutputPort VariantValueOutputPort;

    @Transactional
    @Override
    public ProductVariant createProductVariant(ProductVariant productVariantToCreate) {
        return productVariantOutputPort.createProductVariant(productVariantToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductVariant getProductVariantById(Long id) {
        return productVariantOutputPort.findProductVariantById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductVariant> getAllProductsVariants() {
        return productVariantOutputPort.getAllProductsVariants();
    }

    @Transactional
    @Override
    public ProductVariant updateProductVariant(Long id, ProductVariant productVariantToUpdate) {
        return productVariantOutputPort.updateProductVariant(id, productVariantToUpdate);
    }

    @Transactional
    @Override
    public void deleteProductVariantById(Long id) {
        productVariantOutputPort.deleteProductVariantById(id);
    }

    @Transactional
    public ProductVariant mapProductVariantRequestToProductVariant(ProductVariantRequest productVariant) {
        ProductVariantBuilder productVariantBuilder = ProductVariant.builder()
                .description(productVariant.getDescription())
                .images(productVariant.getImages())
                .price(productVariant.getPrice())
                .sku(productVariant.getSku())
                .stock(productVariant.getStock());

        if (productVariant.getProductId() != null) {
            Product product = productOutputPort.findProductById(productVariant.getProductId());
            productVariantBuilder.product(product);
        }

        if (productVariant.getVariantValueId() != null) {
            VariantValue variantValue = VariantValueOutputPort.findVariantValueById(productVariant.getVariantValueId());

            Set<VariantValue> variantsValues = new HashSet<>();
            variantsValues.add(variantValue);

            productVariantBuilder.variantsValues(variantsValues);
        }

        return productVariantBuilder.build();
    }

}
