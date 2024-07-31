package com.facugl.ecommerce.server.application.service.security;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.security.operations.CreateOperationUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.DeleteOperationUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.GetAllOperationsUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.GetAllPublicsOperationsUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.GetOperationUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.UpdateOperationUseCase;
import com.facugl.ecommerce.server.application.port.output.security.ModuleOutputPort;
import com.facugl.ecommerce.server.application.port.output.security.OperationOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.security.Module;
import com.facugl.ecommerce.server.domain.model.security.Operation;
import com.facugl.ecommerce.server.domain.model.security.Operation.OperationBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.OperationRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class OperationService implements
        CreateOperationUseCase,
        GetOperationUseCase,
        GetAllOperationsUseCase,
        UpdateOperationUseCase,
        DeleteOperationUseCase,
        GetAllPublicsOperationsUseCase {
    private final OperationOutputPort operationOutputPort;
    private final ModuleOutputPort moduleOutputPort;

    @Transactional
    @Override
    public Operation createOperation(Operation operationToCreate) {
        return operationOutputPort.createOperation(operationToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public Operation getOperationById(Long operationId) {
        return operationOutputPort.findOperationById(operationId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operation> getAllPublicsOperations() {
        return operationOutputPort.findOperationByPublicAccess();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Operation> getAllOperations() {
        return operationOutputPort.getAllOperations();
    }

    @Transactional
    @Override
    public Operation updateOperation(Long operationId, Operation operationToUpdate) {
        return operationOutputPort.updateOperation(operationId, operationToUpdate);
    }

    @Transactional
    @Override
    public void deleteOperationById(Long operationId) {
        operationOutputPort.deleteOperationById(operationId);
    }

    @Transactional
    public Operation mapOperationRequestToOperation(OperationRequest operationToCreate) {
        OperationBuilder operation = Operation.builder()
                .name(operationToCreate.getName())
                .path(operationToCreate.getPath())
                .httpMethod(operationToCreate.getHttpMethod())
                .permitAll(operationToCreate.isPermitAll());

        if (operationToCreate.getModuleId() != null) {
            Module module = moduleOutputPort.findModuleById(operationToCreate.getModuleId());
            
            operation.module(module);
        }

        return operation.build();
    }
}
