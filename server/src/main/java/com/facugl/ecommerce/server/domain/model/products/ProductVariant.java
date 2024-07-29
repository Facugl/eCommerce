package com.facugl.ecommerce.server.domain.model.products;

import java.math.BigDecimal;
import java.util.List;

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
public class ProductVariant {
    private Long id;
    private String description;
    private List<String> images;
    private BigDecimal price;
    private String sku;
    private Integer stock;
    private Product product;
    private List<ProductDetail> productsDetails;
}
