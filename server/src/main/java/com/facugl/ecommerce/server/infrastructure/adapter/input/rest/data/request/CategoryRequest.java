package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {

    @NotBlank(message = "Name shouldn't be null or empty")
    @Size(min = 4, max = 100, message = "Name must be between 4 and 100 characters (inclusive)")
    private String name;

    private boolean status;
    
    @Size(max = 100, message = "Parent Category must be less than or equal to 100 characters")
    private String parentCategory;
    
}
