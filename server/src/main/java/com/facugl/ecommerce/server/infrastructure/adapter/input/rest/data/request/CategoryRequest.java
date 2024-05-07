package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.categories.CreateCategoryValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.categories.UpdateCategoryValidationGroup;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
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
public class CategoryRequest {

	@NotBlank(message = "Name shouldn't be null or empty", groups = { CreateCategoryValidationGroup.class })
	@Size(min = 4, max = 100, message = "Name must be between 4 and 100 characters (inclusive)", groups = {
			CreateCategoryValidationGroup.class, UpdateCategoryValidationGroup.class })
	private String name;

	@Enumerated(EnumType.STRING)
	private CategoryStatus status;

	@Positive(message = "Parent Category ID must be a positive number", groups = {
			CreateCategoryValidationGroup.class, UpdateCategoryValidationGroup.class })
	private Long parentCategoryId;

}
