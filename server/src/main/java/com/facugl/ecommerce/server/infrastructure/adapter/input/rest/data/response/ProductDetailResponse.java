package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse implements Serializable {
    private Long id;
    private ProductVariantResponse productVariant;
    private VariantValueResponse variantValue;
}
