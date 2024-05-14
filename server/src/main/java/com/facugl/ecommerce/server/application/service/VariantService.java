package com.facugl.ecommerce.server.application.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.variants.CreateVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.DeleteVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.GetAllVariantsUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.GetVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.UpdateVariantUseCase;
import com.facugl.ecommerce.server.application.port.output.VariantOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.variants.Variant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class VariantService implements
        CreateVariantUseCase,
        GetVariantUseCase,
        GetAllVariantsUseCase,
        DeleteVariantUseCase,
        UpdateVariantUseCase {

    private final VariantOutputPort variantOutputPort;

    @Transactional
    @Override
    public Variant createVariant(Variant variantToCreate) {
        return variantOutputPort.createVariant(variantToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public Variant getVariantById(Long id) {
        return variantOutputPort.findVariantById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Variant getVariantByName(String name) {
        return variantOutputPort.findVariantByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Variant> getAllVariants() {
        return variantOutputPort.getAllVariants();
    }

    @Transactional
    @Override
    public void deleteVariantById(Long id) {
        variantOutputPort.deleteVariantById(id);
    }

    @Transactional
    @Override
    public Variant updateVariant(Long id, Variant variantToUpdate) {
        return variantOutputPort.updateVariant(id, variantToUpdate);
    }

}
