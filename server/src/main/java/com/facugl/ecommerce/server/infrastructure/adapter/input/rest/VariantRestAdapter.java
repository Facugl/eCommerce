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
import com.facugl.ecommerce.server.application.port.input.variants.GetVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variants.UpdateVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetAllVariantsValuesByVariantUseCase;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
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

	@PostMapping
	public ResponseEntity<VariantResponse> createVariant(
			@RequestBody @Validated(CreateVariantValidationGroup.class) VariantRequest variantToCreate) {
		Variant variant = variantMapper.mapVariantRequestToVariant(variantToCreate);

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
		List<VariantResponse> variants = getAllVariantsUseCase.getAllVariants()
				.stream()
				.map(variantMapper::mapVariantToVariantResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(variants);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
		deleteVariantUseCase.deleteVariantById(id);

		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<VariantResponse> updateVariant(
			@PathVariable Long id,
			@RequestBody @Validated(UpdateVariantValidationGroup.class) VariantRequest variantToUpdate) {
		Variant variant = variantMapper.mapVariantRequestToVariant(variantToUpdate);

		Variant updatedVariant = updateVariantUseCase.updateVariant(id, variant);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(variantMapper.mapVariantToVariantResponse(updatedVariant));
	}

	@GetMapping("/{id}/values")
	public ResponseEntity<List<VariantValueResponse>> getAllVariantValues(@PathVariable Long id) {
		List<VariantValueResponse> variantsValues = getAllVariantsValuesByVariantUseCase
				.getAllVariantsValuesByVariant(id)
				.stream()
				.map(variantValueMapper::mapVariantValueToVariantValueResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(variantsValues);
	}
}
