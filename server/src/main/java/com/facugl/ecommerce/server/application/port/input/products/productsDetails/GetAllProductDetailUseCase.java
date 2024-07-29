package com.facugl.ecommerce.server.application.port.input.products.productsDetails;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.ProductDetail;

public interface GetAllProductDetailUseCase {
    List<ProductDetail> getAllProductsDetails();
}
