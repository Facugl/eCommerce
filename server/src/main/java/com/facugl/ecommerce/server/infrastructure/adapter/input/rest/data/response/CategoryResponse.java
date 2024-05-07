package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.util.HashSet;
import java.util.Set;

import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String name;

    private CategoryStatus status;

    private CategoryResponse parentCategory;

    @JsonManagedReference
    private Set<ProductResponse> products = new HashSet<>();

    @JsonManagedReference
    private Set<VariantResponse> variants = new HashSet<>();

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", parentCategory='" + parentCategory.getName() + '\'' +
                ", products=" + products.stream().map(ProductResponse::getName).toList() +
                ", variants=" + variants.stream().map(VariantResponse::getName).toList() +
                "}";
    }

}
