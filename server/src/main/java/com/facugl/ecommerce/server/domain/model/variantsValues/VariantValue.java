package com.facugl.ecommerce.server.domain.model.variantsValues;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VariantValue {

    private Long id;

    private String value;

    private Variant variant;

}
