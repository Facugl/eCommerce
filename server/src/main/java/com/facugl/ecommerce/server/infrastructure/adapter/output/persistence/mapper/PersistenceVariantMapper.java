package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;

@Mapper(componentModel = "spring")
public interface PersistenceVariantMapper {

	@Mapping(target = "id", ignore = true)
	VariantEntity mapVariantToVariantEntity(Variant variant);

	Variant mapVariantEntityToVariant(VariantEntity variantEntity);

}
