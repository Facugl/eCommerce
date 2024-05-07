package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;

public interface ProductVariantOutputPort {

    ProductVariant createProductVariant(ProductVariant productVariantToCreate);

    ProductVariant findProductVariantById(Long id);

    List<ProductVariant> getAllProductsVariants();

    void deleteProductVariantById(Long id);

    ProductVariant updateProductVariant(Long id, ProductVariant productVariantToUpdate);

}
