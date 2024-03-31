package com.facugl.ecommerce.server.application.port.input.variants;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

public interface CreateVariantUseCase {
    Variant createVariant(Variant variantToCreate);
}
