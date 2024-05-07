package com.facugl.ecommerce.server.application.port.input.categories;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.variants.Variant;

public interface GetAllVariantsByCategoryUseCase {

    List<Variant> getAllVariantsByCategory(Long categoryId);

}
