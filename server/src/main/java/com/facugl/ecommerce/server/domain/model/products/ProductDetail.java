package com.facugl.ecommerce.server.domain.model.products;

import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

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
public class ProductDetail {
    private Long id;
    private ProductVariant productVariant;
    private VariantValue variantValue;
}
