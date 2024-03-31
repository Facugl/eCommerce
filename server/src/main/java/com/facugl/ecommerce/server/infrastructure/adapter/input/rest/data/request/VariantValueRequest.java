package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.CreateVariantValueValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.UpdateVariantValueValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VariantValueRequest {

    @NotBlank(message = "Name shouldn't be null or empty", groups = { CreateVariantValueValidationGroup.class })
    @Size(min = 1, max = 5, message = "Name must be between 1 and 5 characters (inclusive)", groups = {
            CreateVariantValueValidationGroup.class, UpdateVariantValueValidationGroup.class })
    private String value;

    @NotBlank(message = "Name shouldn't be null or empty", groups = CreateVariantValueValidationGroup.class)
    @Size(min = 4, max = 15, message = "Name must be between 4 and 15 characters (inclusive)", groups = {
            CreateVariantValueValidationGroup.class, UpdateVariantValueValidationGroup.class })
    private String variantName;

}
