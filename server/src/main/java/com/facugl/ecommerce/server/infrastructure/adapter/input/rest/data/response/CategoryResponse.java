package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

    private CategoryStatus status;

    private Category parentCategory;

}
