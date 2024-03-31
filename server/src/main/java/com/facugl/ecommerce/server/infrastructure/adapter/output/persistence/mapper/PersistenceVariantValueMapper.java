package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;

@Mapper(componentModel = "spring")
public interface PersistenceVariantValueMapper {

	VariantValueEntity mapVariantValueToVariantValueEntity(VariantValue variantvalue);

	VariantValue mapVariantValueEntityToVariantValue(VariantValueEntity entity);

}
