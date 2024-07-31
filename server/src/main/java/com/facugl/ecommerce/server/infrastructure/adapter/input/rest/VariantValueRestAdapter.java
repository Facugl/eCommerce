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

import com.facugl.ecommerce.server.application.mapper.ApplicationVariantValueMapper;
import com.facugl.ecommerce.server.application.port.input.variantsValues.CreateVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.DeleteVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetAllVariantsValuesUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.GetVariantValueUseCase;
import com.facugl.ecommerce.server.application.port.input.variantsValues.UpdateVariantValueUseCase;
import com.facugl.ecommerce.server.application.service.VariantValueService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.VariantValueRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantValueResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.CreateVariantValueValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.UpdateVariantValueValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/variants-values")
@RestController
@WebAdapter
public class VariantValueRestAdapter {
	private final ApplicationVariantValueMapper variantValueMapper;
	private final CreateVariantValueUseCase createVariantValueUseCase;
	private final GetVariantValueUseCase getVariantValueUseCase;
	private final GetAllVariantsValuesUseCase getAllVariantsValuesUseCase;
	private final UpdateVariantValueUseCase updateVariantValueUseCase;
	private final DeleteVariantValueUseCase deleteVariantValueUseCase;
	private final VariantValueService variantValueService;

	@PostMapping
	public ResponseEntity<VariantValueResponse> createVariantValue(
			@RequestBody @Validated(CreateVariantValueValidationGroup.class) VariantValueRequest valueToCreate) {
		VariantValue variantValue = variantValueService.mapVariantValueRequestToVariantValue(valueToCreate);

		VariantValue createdVariantValue = createVariantValueUseCase.createVariantValue(variantValue);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(variantValueMapper.mapVariantValueToVariantValueResponse(createdVariantValue));
	}

	@GetMapping("/{id}")
	public ResponseEntity<VariantValueResponse> getVariantValueById(@PathVariable Long id) {
		VariantValue variantValue = getVariantValueUseCase.findVariantValueById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(variantValueMapper.mapVariantValueToVariantValueResponse(variantValue));
	}

	@GetMapping
	public ResponseEntity<List<VariantValueResponse>> getAllVariantsValues() {
		List<VariantValueResponse> variantsValues = getAllVariantsValuesUseCase.getAllVariantsValues()
				.stream()
				.map(variantValueMapper::mapVariantValueToVariantValueResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(variantsValues);
	}

	@PutMapping("/{id}")
	public ResponseEntity<VariantValueResponse> updateVariantValue(
			@PathVariable Long id,
			@RequestBody @Validated(UpdateVariantValueValidationGroup.class) VariantValueRequest valueToUpdate) {
		VariantValue variantValue = variantValueService.mapVariantValueRequestToVariantValue(valueToUpdate);

		VariantValue updatedVariantValue = updateVariantValueUseCase.updateVariantValue(id, variantValue);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(variantValueMapper.mapVariantValueToVariantValueResponse(updatedVariantValue));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVariantValue(@PathVariable Long id) {
		deleteVariantValueUseCase.deleteVariantValueById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
