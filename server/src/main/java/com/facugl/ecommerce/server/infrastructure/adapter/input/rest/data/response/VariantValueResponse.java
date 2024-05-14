package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantValueResponse {

    private Long id;

    private String value;

    private VariantResponse variant;

    @JsonBackReference
    private Set<ProductVariantResponse> productsVariants = new HashSet<>();

    @Override
    public String toString() {
        return "VariantValueResponse{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", variant=" + variant +
                ", productsVariants=" + productsVariants.stream().map(ProductVariantResponse::getId).toList() +
                "}";
    }

}
