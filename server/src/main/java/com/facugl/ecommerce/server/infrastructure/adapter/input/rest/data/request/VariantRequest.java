package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import java.io.Serializable;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.CreateVariantValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.UpdateVariantValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantRequest implements Serializable {

	@NotBlank(message = "Name shouldn't be null or empty.", groups = { CreateVariantValidationGroup.class })
	@Size(min = 4, max = 15, message = "Name must be between 4 and 15 characters.", groups = {
			CreateVariantValidationGroup.class, UpdateVariantValidationGroup.class })
	private String name;

}
