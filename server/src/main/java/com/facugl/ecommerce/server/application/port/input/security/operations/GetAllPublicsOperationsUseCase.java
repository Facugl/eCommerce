package com.facugl.ecommerce.server.application.port.input.security.operations;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.Operation;

public interface GetAllPublicsOperationsUseCase {
    List<Operation> getAllPublicsOperations();
}
