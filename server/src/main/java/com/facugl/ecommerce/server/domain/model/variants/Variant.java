package com.facugl.ecommerce.server.domain.model.variants;

import java.util.ArrayList;
import java.util.List;

import com.facugl.ecommerce.server.domain.model.categories.Category;
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
public class Variant {

    private Long id;

    private String name;

    private Category category;

    @Builder.Default
    private List<VariantValue> variantValues = new ArrayList<>();

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category.getName() + '\'' +
                ", variantValues=" + variantValues.stream().map(VariantValue::getValue).toList() +
                "}";
    }

}
