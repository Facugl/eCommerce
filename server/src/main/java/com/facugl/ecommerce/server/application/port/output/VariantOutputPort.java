package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

public interface VariantOutputPort {

    Variant createVariant(Variant variantToCreate);

    Variant findVariantById(Long id);

    Variant findVariantByName(String name);

    List<Variant> getAllVariants();

    void deleteVariantById(Long id);

    Variant updateVariant(Long id, Variant variantToUpdate);

    List<VariantValue> getAllVariantsValuesByVariant(Long variantId);

}
