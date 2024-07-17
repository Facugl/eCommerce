package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security;

import java.io.Serializable;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.operations.CreateOperationValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.operations.UpdateOperationValidateGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationRequest implements Serializable {

    @NotBlank(message = "Name shouldn't be null or empty.", groups = { CreateOperationValidationGroup.class })
    @Size(max = 60, message = "Name must be at most 60 characters.", groups = { CreateOperationValidationGroup.class,
            UpdateOperationValidateGroup.class })
    private String name;

    @Size(max = 20, message = "Name must be at most 20 characters.", groups = { CreateOperationValidationGroup.class,
            UpdateOperationValidateGroup.class })
    private String path;

    @NotBlank(message = "Name shouldn't be null or empty.", groups = { CreateOperationValidationGroup.class })
    @Size(max = 10, message = "Name must be at most 10 characters.", groups = { CreateOperationValidationGroup.class,
            UpdateOperationValidateGroup.class })
    private String httpMethod;

    private boolean permitAll;

    @NotNull(message = "moduleId can not be null.", groups = {
            CreateOperationValidationGroup.class })
    @Positive(message = "moduleId must be a positive number.", groups = {
            CreateOperationValidationGroup.class, UpdateOperationValidateGroup.class })
    private Long moduleId;

}
