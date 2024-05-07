package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;

@Mapper(componentModel = "spring")
public interface PersistenceProductMapper {

    @Mapping(target = "id", ignore = true)
    ProductEntity mapProductToProductEntity(Product product,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @InheritInverseConfiguration
    Product mapProductEntityToProduct(ProductEntity productEntity,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default ProductEntity mapProductToProductEntity(Product product) {
        return mapProductToProductEntity(product, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default Product mapProductEntityToProduct(ProductEntity productEntity) {
        return mapProductEntityToProduct(productEntity, new CycleAvoidingMappingContext());
    }

}
