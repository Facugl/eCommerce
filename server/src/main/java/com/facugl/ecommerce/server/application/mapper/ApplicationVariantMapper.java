package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.application.service.CategoryService;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.domain.model.variants.Variant.VariantBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.VariantRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantResponse;

@Mapper(componentModel = "spring")
public interface ApplicationVariantMapper {

    default Variant mapVariantRequestToVariant(VariantRequest request, CategoryService categoryService) {
        VariantBuilder variantBuilder = Variant.builder()
                .name(request.getName());

        if (request.getCategoryName() != null) {
            Category parentCategory = categoryService
                    .getCategoryByName(request.getCategoryName())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category with name: " + request.getCategoryName() + " not found."));

            variantBuilder.category(parentCategory);
        }

        return variantBuilder.build();
    }

    VariantResponse mapVariantToVariantResponse(Variant variant);

}
