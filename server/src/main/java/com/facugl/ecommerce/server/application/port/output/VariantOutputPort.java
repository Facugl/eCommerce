package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

public interface VariantOutputPort {
    Variant createVariant(Variant variantToCreate);

    Variant findVariantById(Long variantId);

    Variant findVariantByName(String variantName);

    List<Variant> getAllVariants();

    void deleteVariantById(Long variantId);

    Variant updateVariant(Long variantId, Variant variantToUpdate);
}
