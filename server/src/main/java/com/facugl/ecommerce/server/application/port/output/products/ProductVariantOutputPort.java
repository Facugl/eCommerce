package com.facugl.ecommerce.server.application.port.output.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.ProductVariant;

public interface ProductVariantOutputPort {

    ProductVariant createProductVariant(ProductVariant productVariantToCreate);

    ProductVariant findProductVariantById(Long productVariantId);

    List<ProductVariant> getAllProductsVariants();

    List<ProductVariant> getAllProductsVariantsByProduct(Long productId);

    List<ProductVariant> getAllProductsVariantsByVariantValue(Long variantValueId);

    void deleteProductVariantById(Long productVariantId);

    ProductVariant updateProductVariant(Long productVariantId, ProductVariant productVariantToUpdate);

}
