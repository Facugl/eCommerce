package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.CreateProductValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.UpdateProductValidationGroup;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {

	@NotBlank(message = "Name shouldn't be null or empty", groups = { CreateProductValidationGroup.class })
	@Size(min = 4, max = 200, message = "Name must be between 4 and 200 characters (inclusive)", groups = {
			CreateProductValidationGroup.class, UpdateProductValidationGroup.class })
	private String name;

	@NotBlank(message = "Description can not be null or empty", groups = { CreateProductValidationGroup.class })
	@Size(min = 4, max = 500, message = "Description must be between 4 and 500 characters (inclusive)", groups = {
			CreateProductValidationGroup.class, UpdateProductValidationGroup.class })
	private String description;

	private String image;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@NotBlank(message = "Category name shouldn't be null or empty", groups = { CreateProductValidationGroup.class })
	@Size(min = 4, max = 100, message = "Category name must be between 4 and 100 characters (inclusive)", groups = {
			CreateProductValidationGroup.class, UpdateProductValidationGroup.class })
	private String categoryName;

}
