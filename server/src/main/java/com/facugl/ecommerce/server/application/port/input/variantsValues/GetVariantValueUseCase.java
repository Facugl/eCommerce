package com.facugl.ecommerce.server.application.port.input.variantsValues;

import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

public interface GetVariantValueUseCase {
    VariantValue findVariantValueById(Long id);
}
