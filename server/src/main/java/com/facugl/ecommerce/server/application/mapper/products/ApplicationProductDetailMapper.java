package com.facugl.ecommerce.server.application.mapper.products;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.products.ProductDetail;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductDetailResponse;

@Mapper(componentModel = "spring")
public interface ApplicationProductDetailMapper {

    ProductDetailResponse mapProductDetailToProductDetailResponse(ProductDetail productDetail,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ProductDetailResponse mapProductDetailToProductDetailResponse(ProductDetail productDetail) {
        return mapProductDetailToProductDetailResponse(productDetail, new CycleAvoidingMappingContext());
    }

}
