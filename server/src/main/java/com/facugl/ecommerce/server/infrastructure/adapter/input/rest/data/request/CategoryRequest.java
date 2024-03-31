package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.categories.CreateCategoryValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.categories.UpdateCategoryValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

	@NotBlank(message = "Name shouldn't be null or empty", groups = { CreateCategoryValidationGroup.class })
	@Size(min = 4, max = 100, message = "Name must be between 4 and 100 characters (inclusive)", groups = {
			CreateCategoryValidationGroup.class, UpdateCategoryValidationGroup.class })
	private String name;

	private CategoryStatus status;

	@Size(max = 100, message = "Parent Category must be less than or equal to 100 characters", groups = {
			CreateCategoryValidationGroup.class, UpdateCategoryValidationGroup.class })
	private String parentCategory;

}
