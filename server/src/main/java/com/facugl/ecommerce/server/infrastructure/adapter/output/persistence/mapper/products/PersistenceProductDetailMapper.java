package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.products;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.products.ProductDetail;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductDetailEntity;

@Mapper(componentModel = "spring")
public interface PersistenceProductDetailMapper {
    
    @Mapping(target = "id", ignore = true)
    ProductDetailEntity mapProductDetailToProductDetailEntity(ProductDetail productDetail,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    ProductDetail mapProductDetailEntityToProductDetail(ProductDetailEntity productDetailEntity,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ProductDetailEntity mapProductDetailToProductDetailEntity(ProductDetail productDetail) {
        return mapProductDetailToProductDetailEntity(productDetail, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default ProductDetail mapProductDetailEntityToProductDetail(ProductDetailEntity productDetailEntity) {
        return mapProductDetailEntityToProductDetail(productDetailEntity, new CycleAvoidingMappingContext());
    }

}
