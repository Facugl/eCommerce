package com.facugl.ecommerce.server.application.port.input.products.productsDetails;

import com.facugl.ecommerce.server.domain.model.products.ProductDetail;

public interface CreateProductDetailUseCase {
    ProductDetail createProductDetail(ProductDetail productDetailToCreate);
}
