package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.products;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.products.ProductVariant;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductVariantEntity;

@Mapper(componentModel = "spring")
public interface PersistenceProductVariantMapper {

	@Mapping(target = "id", ignore = true)
	ProductVariantEntity mapProductVariantToProductVariantEntity(ProductVariant productVariant,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	ProductVariant mapProductVariantEntityToProductVariant(ProductVariantEntity productVariantEntity,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@DoIgnore
	default ProductVariantEntity mapProductVariantToProductVariantEntity(ProductVariant productVariant) {
		return mapProductVariantToProductVariantEntity(productVariant, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default ProductVariant mapProductVariantEntityToProductVariant(ProductVariantEntity productVariantEntity) {
		return mapProductVariantEntityToProductVariant(productVariantEntity, new CycleAvoidingMappingContext());
	}

}
