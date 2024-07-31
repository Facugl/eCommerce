package com.facugl.ecommerce.server.application.port.input.security.modules;

import com.facugl.ecommerce.server.domain.model.security.Module;

public interface UpdateModuleUseCase {
    Module updateModule(Long moduleId, Module moduleToUpdate);
}
