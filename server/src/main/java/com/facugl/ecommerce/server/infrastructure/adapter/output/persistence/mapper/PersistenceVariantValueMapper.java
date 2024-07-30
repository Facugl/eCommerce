package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;

@Mapper(componentModel = "spring")
public interface PersistenceVariantValueMapper {

	@Mapping(target = "id", ignore = true)
	VariantValueEntity mapVariantValueToVariantValueEntity(VariantValue variantValue,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	VariantValue mapVariantValueEntityToVariantValue(VariantValueEntity variantValueEntity,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@DoIgnore
	default VariantValueEntity mapVariantValueToVariantValueEntity(VariantValue variantValue) {
		return mapVariantValueToVariantValueEntity(variantValue, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default VariantValue mapVariantValueEntityToVariantValue(VariantValueEntity variantValueEntity) {
		return mapVariantValueEntityToVariantValue(variantValueEntity, new CycleAvoidingMappingContext());
	}

}
