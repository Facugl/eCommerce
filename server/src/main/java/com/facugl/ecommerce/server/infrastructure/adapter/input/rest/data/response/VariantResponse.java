package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import com.facugl.ecommerce.server.domain.model.categories.Category;

import lombok.Data;

@Data
public class VariantResponse {

    private Long id;

    private String name;

    private Category category;

}
