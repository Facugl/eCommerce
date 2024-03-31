package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.application.service.VariantService;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue.VariantValueBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.VariantValueRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantValueResponse;

@Mapper(componentModel = "spring")
public interface ApplicationVariantValueMapper {

    default VariantValue mapVariantValueRequestToVariantValue(
            VariantValueRequest valueToCreate,
            VariantService variantService) {
        VariantValueBuilder variantValueBuilder = VariantValue.builder()
                .value(valueToCreate.getValue());

        if (valueToCreate.getVariantName() != null) {
            Variant variant = variantService.getVariantByName(valueToCreate.getVariantName());

            variantValueBuilder.variant(variant);
        }

        return variantValueBuilder.build();
    }

    VariantValueResponse mapToVariantResponse(VariantValue value);

}
