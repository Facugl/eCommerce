package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse implements Serializable {
    private Long id;
    private String description;
    private List<String> images;
    private BigDecimal price;
    private String sku;
    private Integer stock;
    private ProductResponse product;
}
