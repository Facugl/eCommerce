package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.VariantOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variants.VariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceVariantMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceVariantValueMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class VariantPersistenceAdapter implements VariantOutputPort {

	private final VariantRepository variantRepository;
	private final CategoryRepository categoryRepository;

	private final PersistenceVariantMapper variantMapper;
	private final PersistenceVariantValueMapper variantValueMapper;

	@Override
	public Variant createVariant(Variant variantToCreate) {
		Long categoryId = variantToCreate.getCategory().getId();

		CategoryEntity categoryEntity = categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " not found."));

		Set<VariantEntity> variantEntityList = categoryEntity.getVariants();

		VariantEntity variantEntity = variantMapper.mapVariantToVariantEntity(variantToCreate);

		if (!variantEntityList.contains(variantEntity)) {
			variantEntity.setCategory(categoryEntity);

			VariantEntity savedVariantEntity = variantRepository.save(variantEntity);

			return variantMapper.mapVariantEntityToVariant(savedVariantEntity);
		} else {
			throw new EntityAlreadyExistsException(
					"Variant with id: " + variantEntity.getId() + " already exists in category with id: "
							+ categoryEntity.getId());
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
	public List<VariantValue> getAllVariantsValuesByVariant(Long variantId) {
		VariantEntity variantEntity = variantRepository
				.findById(variantId)
				.orElseThrow(() -> new EntityNotFoundException("Variant with id: " + variantId + " not found."));

		return variantEntity.getVariantValues()
				.stream()
				.map(variantValueEntity -> variantValueMapper.mapVariantValueEntityToVariantValue(variantValueEntity))
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

		if (variantToUpdate.getCategory() != null) {
			Long categoryId = variantToUpdate.getCategory().getId();

			CategoryEntity categoryEntity = categoryRepository
					.findById(categoryId)
					.orElseThrow(() -> new EntityNotFoundException(
							"Category with id: " + categoryId + " not found."));

			variantEntity.setCategory(categoryEntity);
		}

		variantRepository.save(variantEntity);

		return variantMapper.mapVariantEntityToVariant(variantEntity);
	}

}
