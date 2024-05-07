package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantResponse;

@Mapper(componentModel = "spring")
public interface ApplicationVariantMapper {

    VariantResponse mapVariantToVariantResponse(Variant variant,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default VariantResponse mapVariantToVariantResponse(Variant variant) {
        return mapVariantToVariantResponse(variant, new CycleAvoidingMappingContext());
    }

}
