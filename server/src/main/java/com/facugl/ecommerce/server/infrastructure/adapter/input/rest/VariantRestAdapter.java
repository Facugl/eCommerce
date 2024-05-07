package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.ApplicationVariantMapper;
import com.facugl.ecommerce.server.application.mapper.ApplicationVariantValueMapper;
import com.facugl.ecommerce.server.application.port.input.variants.CreateVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.DeleteVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.GetAllVariantsUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.GetAllVariantsValuesByVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.GetVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.UpdateVariantUseCase;
import com.facugl.ecommerce.server.application.service.VariantService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.VariantRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantValueResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.CreateVariantValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variants.UpdateVariantValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/variants")
@RestController
@WebAdapter
public class VariantRestAdapter {

    private final ApplicationVariantMapper variantMapper;
    private final ApplicationVariantValueMapper variantValueMapper;

    private final CreateVariantUseCase createVariantUseCase;
    private final GetVariantUseCase getVariantUseCase;
    private final GetAllVariantsUseCase getAllVariantsUseCase;
    private final GetAllVariantsValuesByVariantUseCase getAllVariantsValuesByVariantUseCase;
    private final DeleteVariantUseCase deleteVariantUseCase;
    private final UpdateVariantUseCase updateVariantUseCase;

    private final VariantService variantService;

    @PostMapping
    public ResponseEntity<VariantResponse> createVariant(
            @RequestBody @Validated(CreateVariantValidationGroup.class) VariantRequest variantToCreate) {
        Variant variant = variantService.mapVariantRequestToVariant(variantToCreate);

        Variant createdVariant = createVariantUseCase.createVariant(variant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(variantMapper.mapVariantToVariantResponse(createdVariant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariantResponse> getOneVariant(@PathVariable Long id) {
        Variant variant = getVariantUseCase.getVariantById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(variantMapper.mapVariantToVariantResponse(variant));
    }

    @GetMapping
    public ResponseEntity<List<VariantResponse>> getAllVariants() {
        List<Variant> variantList = getAllVariantsUseCase.getAllVariants();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(variantList
                        .stream()
                        .map(variantMapper::mapVariantToVariantResponse)
                        .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        deleteVariantUseCase.deleteVariantById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariantResponse> updateVariant(
            @PathVariable Long id,
            @RequestBody @Validated(UpdateVariantValidationGroup.class) VariantRequest variantToUpdate) {
        Variant variant = variantService.mapVariantRequestToVariant(variantToUpdate);

        Variant updatedVariant = updateVariantUseCase.updateVariant(id, variant);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(variantMapper.mapVariantToVariantResponse(updatedVariant));
    }

    @GetMapping("/{id}/values")
    public ResponseEntity<List<VariantValueResponse>> getAllVariantValues(@PathVariable Long id) {
        List<VariantValue> variantValueList = getAllVariantsValuesByVariantUseCase.getAllVariantsValuesByVariant(id);

        List<VariantValueResponse> variantValueResponseList = variantValueList
                .stream()
                .map(variantValueMapper::mapVariantValueToVariantValueResponse)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(variantValueResponseList);
    }

}
