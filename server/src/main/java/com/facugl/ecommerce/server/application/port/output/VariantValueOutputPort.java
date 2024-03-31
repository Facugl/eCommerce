package com.facugl.ecommerce.server.application.port.output;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

public interface VariantValueOutputPort {

    VariantValue createVariantValue(VariantValue valueToCreate);

    VariantValue findVariantValueById(Long id);

    VariantValue findVariantValueByValue(String name);

    List<VariantValue> getAllVariantsValues();

    void deleteVariantValueById(Long id);

    VariantValue updateVariantValue(Long id, VariantValue valueToUpdate);

}