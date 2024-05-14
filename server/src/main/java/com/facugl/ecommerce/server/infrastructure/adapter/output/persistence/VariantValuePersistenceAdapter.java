package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.VariantValueOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
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
	public VariantValue createVariantValue(VariantValue valueToCreate) {
		Long variantId = valueToCreate.getVariant().getId();

		VariantEntity variantEntity = variantRepository
				.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + variantId + " not found."));

		List<VariantValueEntity> variantValueEntityList = variantValueRepository.findVariantValuesByVariant(variantId);

		VariantValueEntity variantValueEntity = variantValueMapper
				.mapVariantValueToVariantValueEntity(valueToCreate);

		if (!variantValueEntityList.contains(variantValueEntity)) {
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
	public List<VariantValue> getAllVariantsValuesByVariant(Long variantId) {
		List<VariantValueEntity> variantValues = variantValueRepository.findVariantValuesByVariant(variantId);

		return variantValues
				.stream()
				.map(variantValueEntity -> variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity))
				.collect(Collectors.toList());
	}

	@Override
	public List<VariantValue> getAllVariantsValuesByProductVariant(Long productVariantId) {
		List<VariantValueEntity> variantValues = variantValueRepository
				.findVariantValuesByProductVariant(productVariantId);

		return variantValues
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
			Long variantId = valueToUpdate.getVariant().getId();

			VariantEntity variantEntity = variantRepository
					.findById(variantId)
					.orElseThrow(() -> new EntityNotFoundException(
							"Variant with name: " + variantId + " not found."));

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
