package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse {

    private Long id;

    private String description;

    private List<String> images;

    private BigDecimal price;

    private String sku;

    private Integer stock;

    private ProductResponse product;

    @JsonManagedReference
    private Set<VariantValueResponse> variantsValues = new HashSet<>();

    @Override
    public String toString() {
        return "ProductVariantResponse{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                ", stock=" + stock +
                ", product='" + product.getName() + '\'' +
                ", variantsValues=" + variantsValues.stream().map(VariantValueResponse::getValue).toList() +
                "}";
    }

}
