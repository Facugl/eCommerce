package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.CreateProductValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.UpdateProductValidationGroup;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class ProductRequest {

	@NotBlank(message = "Name can not be null or empty", groups = { CreateProductValidationGroup.class })
	@Size(min = 4, max = 200, message = "Name must be between 4 and 200 characters", groups = {
			CreateProductValidationGroup.class, UpdateProductValidationGroup.class })
	private String name;

	@NotBlank(message = "Description can not be null or empty", groups = { CreateProductValidationGroup.class })
	@Size(min = 4, max = 5000, message = "Description must be between 4 and 5000 characters", groups = {
			CreateProductValidationGroup.class, UpdateProductValidationGroup.class })
	private String description;

	@NotEmpty(message = "The list of images can not be empty", groups = { CreateProductValidationGroup.class })
	private List<String> images;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@NotNull(message = "categoryId can not be null", groups = {
			CreateProductValidationGroup.class })
	@Positive(message = "categoryId must be a positive number", groups = {
			CreateProductValidationGroup.class, UpdateProductValidationGroup.class })
	private Long categoryId;

}
