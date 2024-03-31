package com.facugl.ecommerce.server.application.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.variantsValues.CreateVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.DeleteVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetAllVariantsValuesUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.UpdateVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.output.VariantValueOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class VariantValueService implements
        CreateVariantValueUseCase,
        GetVariantValueUseCase,
        GetAllVariantsValuesUseCase,
        UpdateVariantValueUseCase,
        DeleteVariantValueUseCase {

    private final VariantValueOutputPort variantValueOutputPort;

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

}
