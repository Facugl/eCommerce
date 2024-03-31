package com.facugl.ecommerce.server.application.port.input.variants;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

public interface GetAllVariantsUseCase {

    List<Variant> getAllVariants();

    List<Variant> getAllVariantsByCategory(Long categoryId);

}
