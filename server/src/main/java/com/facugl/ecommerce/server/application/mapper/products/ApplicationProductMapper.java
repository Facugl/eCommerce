package com.facugl.ecommerce.server.application.mapper.products;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;

@Mapper(componentModel = "spring")
public interface ApplicationProductMapper {
    ProductResponse mapProductToProductResponse(Product product);
}
