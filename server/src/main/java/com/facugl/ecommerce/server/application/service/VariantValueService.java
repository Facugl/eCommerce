package com.facugl.ecommerce.server.application.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.variantsValues.CreateVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.DeleteVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetAllVariantsValuesByProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetAllVariantsValuesByVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetAllVariantsValuesUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.UpdateVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.output.VariantOutputPort;
import com.facugl.ecommerce.server.application.port.output.VariantValueOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue.VariantValueBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.VariantValueRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class VariantValueService implements
        CreateVariantValueUseCase,
        GetVariantValueUseCase,
        GetAllVariantsValuesUseCase,
        GetAllVariantsValuesByVariantUseCase,
        GetAllVariantsValuesByProductVariantUseCase,
        UpdateVariantValueUseCase,
        DeleteVariantValueUseCase {

    private final VariantValueOutputPort variantValueOutputPort;
    private final VariantOutputPort variantOutputPort;

    @Transactional
    @Override
    public VariantValue createVariantValue(VariantValue valueToCreate) {
        return variantValueOutputPort.createVariantValue(valueToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public VariantValue findVariantValueById(Long id) {
        return variantValueOutputPort.findVariantValueById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VariantValue> getAllVariantsValues() {
        return variantValueOutputPort.getAllVariantsValues();
    }

    @Transactional(readOnly = true)
    @Override
    public List<VariantValue> getAllVariantsValuesByVariant(Long variantId) {
        return variantValueOutputPort.getAllVariantsValuesByVariant(variantId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VariantValue> getAllVariantsValuesByProductVariant(Long productVariantId) {
        return variantValueOutputPort.getAllVariantsValuesByProductVariant(productVariantId);
    }

    @Transactional
    @Override
    public VariantValue updateVariantValue(Long id, VariantValue valueToUpdate) {
        return variantValueOutputPort.updateVariantValue(id, valueToUpdate);
    }

    @Transactional
    @Override
    public void deleteVariantValueById(Long id) {
        variantValueOutputPort.deleteVariantValueById(id);
    }

    @Transactional
    public VariantValue mapVariantValueRequestToVariantValue(VariantValueRequest variantValue) {
        VariantValueBuilder variantValueBuilder = VariantValue.builder()
                .value(variantValue.getValue());

        if (variantValue.getVariantId() != null) {
            Variant variant = variantOutputPort.findVariantById(variantValue.getVariantId());

            variantValueBuilder.variant(variant);
        }

        return variantValueBuilder.build();
    }

}
