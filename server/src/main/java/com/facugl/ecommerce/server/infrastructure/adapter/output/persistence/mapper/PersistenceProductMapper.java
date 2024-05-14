package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;

@Mapper(componentModel = "spring")
public interface PersistenceProductMapper {

    @Mapping(target = "id", ignore = true)
    ProductEntity mapProductToProductEntity(Product product);

    @InheritInverseConfiguration
    Product mapProductEntityToProduct(ProductEntity productEntity);

}
