package com.facugl.ecommerce.server.application.port.input.variantsValues;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

public interface GetAllVariantsValuesByProductVariantUseCase {

    List<VariantValue> getAllVariantsValuesByProductVariant(Long productVariantId);

}
