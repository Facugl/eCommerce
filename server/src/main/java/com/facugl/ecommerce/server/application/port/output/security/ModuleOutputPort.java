package com.facugl.ecommerce.server.application.port.output.security;

import java.util.List;

import com.facugl.ecommerce.server.domain.model.security.Module;

public interface ModuleOutputPort {

    Module createModule(Module moduleToCreate);

    Module findModuleById(Long moduleId);

    List<Module> getAllModules();

    Module updateModule(Long moduleId, Module moduleToUpdate);

    void deleteModuleById(Long moduleId);

}
