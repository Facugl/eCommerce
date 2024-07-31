package com.facugl.ecommerce.server.application.port.input.security.modules;

import com.facugl.ecommerce.server.domain.model.security.Module;

public interface CreateModuleUseCase {
    Module createModule(Module moduleToCreate);
}
