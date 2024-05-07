package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;

@Mapper(componentModel = "spring")
public interface PersistenceVariantMapper {

	@Mapping(target = "id", ignore = true)
	VariantEntity mapVariantToVariantEntity(Variant variant,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@InheritInverseConfiguration
	Variant mapVariantEntityToVariant(VariantEntity variantEntity,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@DoIgnore
	default VariantEntity mapVariantToVariantEntity(Variant variant) {
		return mapVariantToVariantEntity(variant, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default Variant mapVariantEntityToVariant(VariantEntity variantEntity) {
		return mapVariantEntityToVariant(variantEntity, new CycleAvoidingMappingContext());
	}

}
