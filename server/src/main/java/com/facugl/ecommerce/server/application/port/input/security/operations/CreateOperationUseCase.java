package com.facugl.ecommerce.server.application.port.input.security.operations;

import com.facugl.ecommerce.server.domain.model.security.Operation;

public interface CreateOperationUseCase {
    Operation createOperation(Operation operationToCreate);
}
