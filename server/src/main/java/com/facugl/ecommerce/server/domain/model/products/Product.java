package com.facugl.ecommerce.server.domain.model.products;

import com.facugl.ecommerce.server.domain.model.categories.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private Long id;

    private String name;

    private String description;

    private String image;

    private ProductStatus status;

    private Category category;

}