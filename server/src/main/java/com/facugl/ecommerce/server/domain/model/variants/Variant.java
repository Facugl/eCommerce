package com.facugl.ecommerce.server.domain.model.variants;

import com.facugl.ecommerce.server.domain.model.categories.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Variant {

    private Long id;

    private String name;

    private Category category;

}
