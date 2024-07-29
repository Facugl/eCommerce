package com.facugl.ecommerce.server.application.mapper.products;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.products.ProductVariant;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductVariantResponse;

@Mapper(componentModel = "spring")
public interface ApplicationProductVariantMapper {

    ProductVariantResponse mapProductVariantToProductVariantResponse(ProductVariant product,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ProductVariantResponse mapProductVariantToProductVariantResponse(ProductVariant product) {
        return mapProductVariantToProductVariantResponse(product, new CycleAvoidingMappingContext());
    }

}
