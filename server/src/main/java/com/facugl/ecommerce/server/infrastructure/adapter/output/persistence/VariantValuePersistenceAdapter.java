package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.VariantValueOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceVariantValueMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantValueRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class VariantValuePersistenceAdapter implements VariantValueOutputPort {
	private final VariantValueRepository variantValueRepository;
	private final VariantRepository variantRepository;
	private final PersistenceVariantValueMapper variantValueMapper;

	@Override
	public VariantValue createVariantValue(VariantValue variantValueToCreate) {
		Long variantId = variantValueToCreate.getVariant().getId();

		VariantEntity variantEntity = variantRepository
				.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + variantId + " not found."));

		List<VariantValueEntity> variantsValues = variantValueRepository.findVariantValuesByVariant(variantId);

		VariantValueEntity variantValueEntity = variantValueMapper
				.mapVariantValueToVariantValueEntity(variantValueToCreate);

		if (!variantsValues.contains(variantValueEntity)) {
			variantValueEntity.setVariant(variantEntity);

			variantValueRepository.save(variantValueEntity);

			return variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity);
		} else {
			throw new EntityAlreadyExistsException(
					"Variant value with id: " + variantValueEntity.getId() + " already exists in variant with id: "
							+ variantId);
		}
	}

	@Override
	public VariantValue findVariantValueById(Long variantValueId) {
		return variantValueRepository
				.findVariantValueWithVariantById(variantValueId)
				.map(variantValueMapper::mapVariantValueEntityToVariantValue)
				.orElseThrow(
						() -> new EntityNotFoundException("Variant value with id: " + variantValueId + " not found."));
	}

	@Override
	public VariantValue findVariantValueByValue(String value) {
		return variantValueRepository
				.findVariantValueByValue(value)
				.map(variantValueMapper::mapVariantValueEntityToVariantValue)
				.orElseThrow(() -> new EntityNotFoundException("Variant with value: " + value + " not found."));
	}

	@Override
	public List<VariantValue> getAllVariantsValues() {
		return variantValueRepository.findAllVariantValuesWithVariant()
				.stream()
				.map(variantValueMapper::mapVariantValueEntityToVariantValue)
				.collect(Collectors.toList());
	}

	@Override
	public List<VariantValue> getAllVariantsValuesByVariant(Long variantId) {
		return variantValueRepository.findVariantValuesByVariant(variantId)
				.stream()
				.map(variantValueMapper::mapVariantValueEntityToVariantValue)
				.collect(Collectors.toList());
	}

	@Override
	public VariantValue updateVariantValue(Long variantValueId, VariantValue variantValueToUpdate) {
		VariantValueEntity variantValueEntity = variantValueRepository
				.findVariantValueWithVariantById(variantValueId)
				.orElseThrow(
						() -> new EntityNotFoundException("Variant value with id: " + variantValueId + " not found."));

		if (variantValueToUpdate.getValue() != null) {
			variantValueEntity.setValue(variantValueToUpdate.getValue());
		}

		if (variantValueToUpdate.getVariant() != null) {
			Long variantId = variantValueToUpdate.getVariant().getId();

			VariantEntity variantEntity = variantRepository
					.findById(variantId)
					.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + variantId + " not found."));

			variantValueEntity.setVariant(variantEntity);
		}

		variantValueRepository.save(variantValueEntity);

		return variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity);
	}

	@Override
	public void deleteVariantValueById(Long variantValueId) {
		VariantValueEntity variantValueEntity = variantValueRepository
				.findVariantValueWithVariantById(variantValueId)
				.orElseThrow(
						() -> new EntityNotFoundException("Variant value with id: " + variantValueId + " not found."));

		variantValueRepository.delete(variantValueEntity);
	}
}
