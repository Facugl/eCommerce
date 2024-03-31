package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

import lombok.Data;

@Data
public class VariantValueResponse {

    private Long id;

    private String value;

    private Variant variant;

}
