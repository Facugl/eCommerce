package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.security.OperationOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.security.Operation;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.ModuleEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.OperationEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security.PersistenceOperationMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.ModuleRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.OperationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class OperationPersistenceAdapter implements OperationOutputPort {
    private final OperationRepository operationRepository;
    private final ModuleRepository moduleRepository;
    private final PersistenceOperationMapper operationMapper;

    @Override
    public Operation createOperation(Operation operationToCreate) {
        if (operationRepository.isNameUnique(operationToCreate.getName())) {
            OperationEntity operationEntity = operationMapper.mapOperationToOperationEntity(operationToCreate);

            OperationEntity savedOperationEntity = operationRepository.save(operationEntity);

            return operationMapper.mapOperationEntityToOperation(savedOperationEntity);
        } else {
            throw new EntityAlreadyExistsException(
                    "Operation with name: " + operationToCreate.getName() + " already exists.");
        }
    }

    @Override
    public void deleteOperationById(Long operationId) {
        OperationEntity operationEntity = operationRepository
                .findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + operationId + " not found."));

        operationRepository.delete(operationEntity);
    }

    @Override
    public Operation findOperationById(Long operationId) {
        return operationRepository
                .findById(operationId)
                .map(operationMapper::mapOperationEntityToOperation)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + operationId + " not found."));
    }

    @Override
    public List<Operation> findOperationByPublicAccess() {
        return operationRepository.findByPublicAccess()
                .stream()
                .map(operationMapper::mapOperationEntityToOperation)
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository
                .findAll()
                .stream()
                .map(operationMapper::mapOperationEntityToOperation)
                .collect(Collectors.toList());
    }

    @Override
    public Operation updateOperation(Long operationId, Operation operationToUpdate) {
        OperationEntity operationEntity = operationRepository
                .findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("Operation with id: " + operationId + " not found."));

        if (operationToUpdate.getName() != null) {
            operationEntity.setName(operationToUpdate.getName());
        }

        if (operationToUpdate.getPath() != null) {
            operationEntity.setPath(operationToUpdate.getPath());
        }

        if (operationToUpdate.getHttpMethod() != null) {
            operationEntity.setHttpMethod(operationToUpdate.getHttpMethod());
        }

        operationEntity.setPermitAll(operationToUpdate.isPermitAll());

        if (operationToUpdate.getModule() != null) {
            Long moduleId = operationToUpdate.getModule().getId();

            ModuleEntity moduleEntity = moduleRepository
                    .findById(moduleId)
                    .orElseThrow(() -> new EntityNotFoundException("Module with id: " + moduleId + " not found."));

            operationEntity.setModule(moduleEntity);
        }

        OperationEntity savedOperationEntity = operationRepository.save(operationEntity);

        return operationMapper.mapOperationEntityToOperation(savedOperationEntity);
    }
}
