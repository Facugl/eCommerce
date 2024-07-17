package com.facugl.ecommerce.server.application.service.security;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.facugl.ecommerce.server.application.port.input.security.modules.CreateModuleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.DeleteModuleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.GetAllModulesUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.GetModuleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.UpdateModuleUseCase;
import com.facugl.ecommerce.server.application.port.output.security.ModuleOutputPort;
import com.facugl.ecommerce.server.common.UseCase;
import com.facugl.ecommerce.server.domain.model.security.Module;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class ModuleService implements
        CreateModuleUseCase,
        GetModuleUseCase,
        GetAllModulesUseCase,
        UpdateModuleUseCase,
        DeleteModuleUseCase {
    private final ModuleOutputPort moduleOutputPort;

    @Transactional
    @Override
    public Module createModule(Module moduleToCreate) {
        return moduleOutputPort.createModule(moduleToCreate);
    }

    @Transactional(readOnly = true)
    @Override
    public Module getModuleById(Long moduleId) {
        return moduleOutputPort.findModuleById(moduleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Module> getAllModules() {
        return moduleOutputPort.getAllModules();
    }

    @Transactional
    @Override
    public Module updateModule(Long moduleId, Module moduleToUpdate) {
        return moduleOutputPort.updateModule(moduleId, moduleToUpdate);
    }

    @Transactional
    @Override
    public void deleteModuleById(Long moduleId) {
        moduleOutputPort.deleteModuleById(moduleId);
    }
}
