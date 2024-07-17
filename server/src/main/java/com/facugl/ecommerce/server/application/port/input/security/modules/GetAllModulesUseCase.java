package com.facugl.ecommerce.server.application.port.input.security.modules;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.Module;

public interface GetAllModulesUseCase {
    List<Module> getAllModules();
}
