package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.util.ArrayList;
import java.util.List;

import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
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
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private List<String> images;

    private ProductStatus status;

    @JsonBackReference
    private CategoryResponse category;

    @JsonManagedReference
    private List<ProductVariantResponse> productsVariants = new ArrayList<>();

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", status='" + status + '\'' +
                ", category='" + category.getName() + '\'' +
                ", productsVariants=" + productsVariants.stream().map(ProductVariantResponse::getId).toList() +
                "}";
    }

}
