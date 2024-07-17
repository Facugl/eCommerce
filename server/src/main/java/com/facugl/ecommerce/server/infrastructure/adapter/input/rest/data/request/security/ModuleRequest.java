package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security;

import java.io.Serializable;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.modules.CreateModuleValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.modules.UpdateModuleValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRequest implements Serializable {

    @NotBlank(message = "Name shouldn't be null or empty.", groups = { CreateModuleValidationGroup.class })
    @Size(max = 20, message = "Name must be at most 20 characters.", groups = { CreateModuleValidationGroup.class,
            UpdateModuleValidationGroup.class })
    private String name;

    @NotBlank(message = "Base path shouldn't be null or empty.", groups = { CreateModuleValidationGroup.class })
    @Size(max = 20, message = "Base path must be at most 20 characters.", groups = { CreateModuleValidationGroup.class,
            UpdateModuleValidationGroup.class })
    private String basePath;

}
