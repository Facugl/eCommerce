package com.facugl.ecommerce.server.domain.model.products;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.categories.Category;

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
public class Product {

    private Long id;
    private String name;
    private String description;
    private List<String> images;
    private ProductStatus status;
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", status='" + status + '\'' +
                ", category='" + category +
                "}";
    }
    
}