package com.facugl.ecommerce.server.application.port.output.security;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.Operation;

public interface OperationOutputPort {

    Operation createOperation(Operation operationToCreate);

    Operation findOperationById(Long operationId);

    List<Operation> getAllOperations();

    Operation updateOperation(Long operationId, Operation operationToUpdate);

    void deleteOperationById(Long operationId);

    List<Operation> findOperationByPublicAccess();

}
