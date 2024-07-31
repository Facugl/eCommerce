package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.VariantOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceVariantMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantValueRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class VariantPersistenceAdapter implements VariantOutputPort {
	private final VariantRepository variantRepository;
	private final VariantValueRepository variantValueRepository;
	private final PersistenceVariantMapper variantMapper;

	@Override
	public Variant createVariant(Variant variantToCreate) {
		if (variantRepository.isVariantNameUnique(variantToCreate.getName())) {
			VariantEntity variantEntity = variantMapper.mapVariantToVariantEntity(variantToCreate);

			VariantEntity savedVariantEntity = variantRepository.save(variantEntity);

			return variantMapper.mapVariantEntityToVariant(savedVariantEntity);
		} else {
			throw new EntityAlreadyExistsException(
					"Variant with name: " + variantToCreate.getName() + " already exists.");
		}
	}

	@Override
	public Variant findVariantById(Long variantId) {
		return variantRepository
				.findById(variantId)
				.map(variantMapper::mapVariantEntityToVariant)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + variantId + " not found."));
	}

	@Override
	public Variant findVariantByName(String variantName) {
		return variantRepository
				.findByName(variantName)
				.map(variantMapper::mapVariantEntityToVariant)
				.orElseThrow(() -> new EntityNotFoundException("Variant with name: " + variantName + " not found."));
	}

	@Override
	public List<Variant> getAllVariants() {
		return variantRepository
				.findAll()
				.stream()
				.map(variantMapper::mapVariantEntityToVariant)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteVariantById(Long variantId) {
		VariantEntity variantEntity = variantRepository
				.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + variantId + " not found."));

		variantValueRepository.deleteByVariant(variantEntity);
		variantRepository.delete(variantEntity);
	}

	@Override
	public Variant updateVariant(Long variantId, Variant variantToUpdate) {
		VariantEntity variantEntity = variantRepository
				.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + variantId + " not found."));

		if (variantToUpdate.getName() != null) {
			variantEntity.setName(variantToUpdate.getName());
		}

		variantRepository.save(variantEntity);

		return variantMapper.mapVariantEntityToVariant(variantEntity);
	}
}
