package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.VariantValueOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityNameNotUniqueException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceVariantMapper;
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
	private final PersistenceVariantMapper variantMapper;

	@Override
	public VariantValue createVariantValue(VariantValue valueToCreate) {
		String value = valueToCreate.getValue();
		String variantName = valueToCreate.getVariant().getName();

		VariantEntity variantEntity = variantRepository
				.findByName(variantName)
				.orElseThrow(() -> new EntityNotFoundException("Variant with name: " + variantName + " not found."));

		List<VariantValueEntity> variantValueEntityList = variantValueRepository
				.findyByVariantNameAndValue(variantName, value);

		if (variantValueEntityList.isEmpty()) {
			VariantValueEntity variantValueEntity = variantValueMapper
					.mapVariantValueToVariantValueEntity(valueToCreate);

			variantValueEntity.setVariant(variantEntity);

			variantValueRepository.save(variantValueEntity);

			return variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity);
		} else {
			throw new EntityNameNotUniqueException("Variant with value: " + valueToCreate.getValue() + " already exist.");
		}
	}

	@Override
	public VariantValue findVariantValueById(Long id) {
		return variantValueRepository
				.findById(id)
				.map(variantValueEntity -> variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity))
				.orElseThrow(() -> new EntityNotFoundException(
						"Variant value with id: " + id + " not found."));
	}

	@Override
	public VariantValue findVariantValueByValue(String value) {
		return variantValueRepository
				.findVariantValueByValue(value)
				.map(variantValueEntity -> variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity))
				.orElseThrow(() -> new EntityNotFoundException("Variant with value: " + value + " not found."));
	}

	@Override
	public List<VariantValue> getAllVariantsValues() {
		return variantValueRepository
				.findAll()
				.stream()
				.map(variantValueEntity -> variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity))
				.collect(Collectors.toList());
	}

	@Override
	public VariantValue updateVariantValue(Long id, VariantValue valueToUpdate) {
		VariantValueEntity variantValueEntity = variantValueRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Variant value with id: " + id + " not found."));

		if (valueToUpdate.getValue() != null) {
			variantValueEntity.setValue(valueToUpdate.getValue());
		}

		if (valueToUpdate.getVariant() != null) {
			VariantEntity variantEntity = variantRepository
					.findByName(valueToUpdate.getVariant().getName())
					.orElseThrow(() -> new EntityNotFoundException(
							"Variant with name: " + valueToUpdate.getVariant().getName() + " not found."));

			variantValueEntity.setVariant(variantEntity);
		}

		variantValueRepository.save(variantValueEntity);

		return variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity);
	}

	@Override
	public void deleteVariantValueById(Long id) {
		VariantValueEntity variantValueEntity = variantValueRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"Variant value with id: " + id + " not found."));

		variantValueRepository.delete(variantValueEntity);
	}

}
