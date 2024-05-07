package com.facugl.ecommerce.server.domain.model.variantsValues;

import java.util.HashSet;
import java.util.Set;

import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;
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
public class VariantValue {

    private Long id;

    private String value;

    private Variant variant;

    @Builder.Default
    private Set<ProductVariant> productsVariants = new HashSet<>();

    @Override
    public String toString() {
        return "VariantValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", variant='" + variant.getName() + '\'' +
                ", productsVariants=" + productsVariants.stream().map(ProductVariant::getId).toList() +
                "}";
    }

}
