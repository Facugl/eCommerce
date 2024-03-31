package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private String image;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Category category;

}
