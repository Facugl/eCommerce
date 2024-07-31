package com.facugl.ecommerce.server.domain.model.variantsValues;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantValue {
    private Long id;
    private String value;
    private Variant variant;
}
