package com.facugl.ecommerce.server.application.port.input.security.operations;

import com.facugl.ecommerce.server.domain.model.security.Operation;

public interface UpdateOperationUseCase {
    Operation updateOperation(Long operationId, Operation operationToUpdate);
}
