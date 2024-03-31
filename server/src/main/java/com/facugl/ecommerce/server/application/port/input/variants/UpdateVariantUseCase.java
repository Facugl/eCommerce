package com.facugl.ecommerce.server.application.port.input.variants;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

public interface UpdateVariantUseCase {
    Variant updateVariant(Long id, Variant variantToUpdate);
}
