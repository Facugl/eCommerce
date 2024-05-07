package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.common.mapping.CycleAvoidingMappingContext;
import com.facugl.ecommerce.server.common.mapping.DoIgnore;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantValueResponse;

@Mapper(componentModel = "spring")
public interface ApplicationVariantValueMapper {

    VariantValueResponse mapVariantValueToVariantValueResponse(VariantValue value,
            @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    @DoIgnore
    default VariantValueResponse mapVariantValueToVariantValueResponse(VariantValue value) {
        return mapVariantValueToVariantValueResponse(value, new CycleAvoidingMappingContext());
    }

}
