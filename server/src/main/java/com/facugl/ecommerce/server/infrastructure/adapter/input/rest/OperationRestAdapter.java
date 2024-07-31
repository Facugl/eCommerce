package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.security.ApplicationOperationMapper;
import com.facugl.ecommerce.server.application.port.input.security.operations.CreateOperationUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.DeleteOperationUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.GetAllOperationsUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.GetOperationUseCase;
import com.facugl.ecommerce.server.application.port.input.security.operations.UpdateOperationUseCase;
import com.facugl.ecommerce.server.application.service.security.OperationService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.security.Operation;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.OperationRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.OperationResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.operations.CreateOperationValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.operations.UpdateOperationValidateGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/operations")
@RestController
@WebAdapter
public class OperationRestAdapter {
    private final ApplicationOperationMapper operationMapper;
    private final CreateOperationUseCase createOperationUseCase;
    private final GetOperationUseCase getOperationUseCase;
    private final GetAllOperationsUseCase getAllOperationsUseCase;
    private final UpdateOperationUseCase updateOperationUseCase;
    private final DeleteOperationUseCase deleteOperationUseCase;
    private final OperationService operationService;

    @PostMapping
    public ResponseEntity<OperationResponse> createOperation(
            @Validated(CreateOperationValidationGroup.class) @RequestBody OperationRequest operationToCreate) {
        Operation operation = operationService.mapOperationRequestToOperation(operationToCreate);

        Operation createdOperation = createOperationUseCase.createOperation(operation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(operationMapper.mapOperationToOperationResponse(createdOperation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationResponse> getOneOperation(@PathVariable Long id) {
        Operation operation = getOperationUseCase.getOperationById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(operationMapper.mapOperationToOperationResponse(operation));
    }

    @GetMapping()
    public ResponseEntity<List<OperationResponse>> getAllOperations() {
        List<OperationResponse> operationResponses = getAllOperationsUseCase.getAllOperations()
                .stream()
                .map(operationMapper::mapOperationToOperationResponse)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(operationResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationResponse> updateOperation(
            @PathVariable Long id,
            @Validated(UpdateOperationValidateGroup.class) @RequestBody OperationRequest operationToCreate) {
        Operation operation = operationService.mapOperationRequestToOperation(operationToCreate);

        Operation savedOperation = updateOperationUseCase.updateOperation(id, operation);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(operationMapper.mapOperationToOperationResponse(savedOperation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationById(@PathVariable Long id) {
        deleteOperationUseCase.deleteOperationById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
