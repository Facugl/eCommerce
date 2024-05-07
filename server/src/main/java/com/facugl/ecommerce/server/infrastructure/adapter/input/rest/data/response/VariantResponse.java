package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantResponse {

    private Long id;

    private String name;

    @JsonBackReference
    private CategoryResponse category;

    @JsonManagedReference
    private Set<VariantValueResponse> variantValues = new HashSet<>();

    @Override
    public String toString() {
        return "VariantResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category.getName() + '\'' +
                ", variantValues=" + variantValues.stream().map(VariantValueResponse::getValue).toList() +
                "}";
    }

}
