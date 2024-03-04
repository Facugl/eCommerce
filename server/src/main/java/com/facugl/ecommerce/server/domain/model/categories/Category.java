package com.facugl.ecommerce.server.domain.model.categories;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {

    private Long id;

    private String name;

    private CategoryStatus status;

    private Category parentCategory;

}
