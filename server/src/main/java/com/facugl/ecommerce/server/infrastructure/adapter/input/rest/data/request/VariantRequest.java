package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.CreateVariantValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.UpdateVariantValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VariantRequest {

    @NotBlank(message = "Name shouldn't be null or empty", groups = { CreateVariantValidationGroup.class })
    @Size(min = 4, max = 15, message = "Name must be between 4 and 15 characters (inclusive)", groups = {
            CreateVariantValidationGroup.class, UpdateVariantValidationGroup.class })
    private String name;

    @NotBlank(message = "Category name shouldn't be null or empty", groups = { CreateVariantValidationGroup.class })
    @Size(min = 4, max = 100, message = "Category name must be between 4 and 100 characters (inclusive)", groups = {
            CreateVariantValidationGroup.class, UpdateVariantValidationGroup.class })
    private String categoryName;

}
