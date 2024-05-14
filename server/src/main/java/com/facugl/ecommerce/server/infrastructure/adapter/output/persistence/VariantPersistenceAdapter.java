package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.VariantOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceVariantMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class VariantPersistenceAdapter implements VariantOutputPort {

	private final VariantRepository variantRepository;

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
	public Variant findVariantById(Long id) {
		return variantRepository
				.findById(id)
				.map(variantEntity -> variantMapper.mapVariantEntityToVariant(variantEntity))
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + id + " not found."));
	}

	@Override
	public Variant findVariantByName(String name) {
		return variantRepository
				.findByName(name)
				.map(variantEntity -> variantMapper.mapVariantEntityToVariant(variantEntity))
				.orElseThrow(() -> new EntityNotFoundException("Variant with name: " + name + " not found."));
	}

	@Override
	public List<Variant> getAllVariants() {
		return variantRepository
				.findAll()
				.stream()
				.map(variantEntity -> variantMapper.mapVariantEntityToVariant(variantEntity))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteVariantById(Long id) {
		VariantEntity variantEntity = variantRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + id + " not found."));

		variantRepository.delete(variantEntity);
	}

	@Override
	public Variant updateVariant(Long id, Variant variantToUpdate) {
		VariantEntity variantEntity = variantRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + id + " not found."));

		if (variantToUpdate.getName() != null) {
			variantEntity.setName(variantToUpdate.getName());
		}

		variantRepository.save(variantEntity);

		return variantMapper.mapVariantEntityToVariant(variantEntity);
	}

}
