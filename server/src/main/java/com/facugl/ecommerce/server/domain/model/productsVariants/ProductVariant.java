package com.facugl.ecommerce.server.domain.model.productsVariants;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

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

    @Builder.Default
    private Set<VariantValue> variantsValues = new HashSet<>();

    @Override
    public String toString() {
        return "ProductVariant{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                ", stock=" + stock +
                ", product='" + product.getName() + '\'' +
                ", variantsValues=" + variantsValues.stream().map(VariantValue::getValue).toList() +
                "}";
    }

}
