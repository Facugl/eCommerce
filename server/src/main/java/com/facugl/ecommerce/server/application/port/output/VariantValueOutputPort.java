package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

public interface VariantValueOutputPort {

    VariantValue createVariantValue(VariantValue variantValueToCreate);

    VariantValue findVariantValueById(Long variantValueId);

    VariantValue findVariantValueByValue(String variantValue);

    List<VariantValue> getAllVariantsValues();

    List<VariantValue> getAllVariantsValuesByVariant(Long variantId);

    void deleteVariantValueById(Long variantValueId);

    VariantValue updateVariantValue(Long variantValueId, VariantValue variantValueToUpdate);

}