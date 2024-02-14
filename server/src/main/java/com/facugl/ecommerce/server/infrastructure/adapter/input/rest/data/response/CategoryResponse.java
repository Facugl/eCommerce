package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import com.facugl.ecommerce.server.domain.model.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {

    private Long id;
    
    private String name;

    private boolean status;

    private Category parentCategory;
    
}
