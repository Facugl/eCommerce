package com.facugl.ecommerce.server.application.port.input.variantsValues;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

public interface GetAllVariantsValuesByVariantUseCase {

    List<VariantValue> getAllVariantsValuesByVariant(Long variantId);

}
