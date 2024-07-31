package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.VariantRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantResponse;

@Mapper(componentModel = "spring")
public interface ApplicationVariantMapper {

    @Mapping(target = "id", ignore = true)
    Variant mapVariantRequestToVariant(VariantRequest variant);

    VariantResponse mapVariantToVariantResponse(Variant variant);

}
