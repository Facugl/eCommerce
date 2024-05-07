package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.CreateVariantValueValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.UpdateVariantValueValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantValueRequest {

	@NotBlank(message = "Name shouldn't be null or empty", groups = { CreateVariantValueValidationGroup.class })
	@Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters (inclusive)", groups = {
			CreateVariantValueValidationGroup.class, UpdateVariantValueValidationGroup.class })
	private String value;

	@NotNull(message = "Variant ID cannot be null", groups = {
			CreateVariantValueValidationGroup.class })
	@Positive(message = "Variant ID must be a positive number", groups = {
			CreateVariantValueValidationGroup.class, UpdateVariantValueValidationGroup.class })
	private Long variantId;

}
