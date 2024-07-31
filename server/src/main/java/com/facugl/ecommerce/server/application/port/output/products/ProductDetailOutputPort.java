package com.facugl.ecommerce.server.application.port.output.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.ProductDetail;

public interface ProductDetailOutputPort {

    ProductDetail createProductDetail(ProductDetail productDetailToCreate);

    ProductDetail findProductDetailById(Long productDetailId);

    List<ProductDetail> getAllProductDetail();

    ProductDetail updateProductDetail(Long productDetailId, ProductDetail productDetailToUpdate);

    void deleteProductDetailById(Long productDetailId);

}
