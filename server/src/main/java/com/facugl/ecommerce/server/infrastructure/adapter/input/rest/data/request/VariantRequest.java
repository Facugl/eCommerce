package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.CreateVariantValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.UpdateVariantValidationGroup;

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
public class VariantRequest {

	@NotBlank(message = "Name shouldn't be null or empty", groups = { CreateVariantValidationGroup.class })
	@Size(min = 4, max = 15, message = "Name must be between 4 and 15 characters (inclusive)", groups = {
			CreateVariantValidationGroup.class, UpdateVariantValidationGroup.class })
	private String name;

	@NotNull(message = "Category ID cannot be null", groups = {
			CreateVariantValidationGroup.class })
	@Positive(message = "Category ID must be a positive number", groups = {
			CreateVariantValidationGroup.class, UpdateVariantValidationGroup.class })
	private Long categoryId;

}
