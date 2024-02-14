package com.facugl.ecommerce.server.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private Long id;
    private String name;
    private boolean status;
    private Category parentCategory;
}
