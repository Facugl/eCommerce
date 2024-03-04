package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {

    @NotBlank(message = "Name shouldn't be null or empty")
    @Size(min = 4, max = 200, message = "Name must be between 4 and 200 characters (inclusive)")
    private String name;

    @NotBlank(message = "Description can not be null or empty")
    @Size(min = 4, max = 500, message = "Description must be between 4 and 500 characters (inclusive)")
    private String description;

    private String image;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @NotBlank(message = "Category name shouldn't be null or empty")
    @Size(min = 4, max = 100, message = "Category name must be between 4 and 100 characters (inclusive)")
    private String categoryName;

}
