package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;

@Mapper(componentModel = "spring")
public interface ApplicationProductMapper {

    ProductResponse mapProductToProductResponse(Product product,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ProductResponse mapProductToProductResponse(Product product) {
        return mapProductToProductResponse(product, new CycleAvoidingMappingContext());
    }

}
