package com.facugl.ecommerce.server.domain.model.categories;

import java.util.HashSet;
import java.util.Set;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.variants.Variant;

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
public class Category {

    private Long id;

    private String name;

    private CategoryStatus status;

    private Category parentCategory;

    @Builder.Default
    private Set<Product> products = new HashSet<>();

    @Builder.Default
    private Set<Variant> variants = new HashSet<>();

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", parentCategory='" + parentCategory.getName() + '\'' +
                ", products=" + products.stream().map(Product::getName).toList() +
                ", variants=" + variants.stream().map(Variant::getName).toList() +
                "}";
    }

}
