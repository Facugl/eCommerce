package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.security.ModuleOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.security.Module;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.security.ModuleEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.security.PersistenceModuleMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.security.ModuleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ModulePersistenceAdapter implements ModuleOutputPort {
    private final ModuleRepository moduleRepository;
    private final PersistenceModuleMapper moduleMapper;

    @Override
    public Module createModule(Module moduleToCreate) {
        if (moduleRepository.isNameUnique(moduleToCreate.getName())) {
            ModuleEntity moduleEntity = moduleMapper.mapModuleToModuleEntity(moduleToCreate);

            ModuleEntity savedModuleEntity = moduleRepository.save(moduleEntity);

            return moduleMapper.mapModuleEntityToModule(savedModuleEntity);
        } else {
            throw new EntityAlreadyExistsException(
                    "Module with name: " + moduleToCreate.getName() + " already exists.");
        }
    }

    @Override
    public void deleteModuleById(Long moduleId) {
        ModuleEntity moduleEntity = moduleRepository
                .findById(moduleId)
                .orElseThrow(() -> new EntityNotFoundException("Module with id: " + moduleId + " not found."));

        moduleRepository.delete(moduleEntity);

    }

    @Override
    public Module findModuleById(Long moduleId) {
        return moduleRepository
                .findById(moduleId)
                .map(moduleMapper::mapModuleEntityToModule)
                .orElseThrow(() -> new EntityNotFoundException("Module with id: " + moduleId + " not found."));
    }

    @Override
    public List<Module> getAllModules() {
        return moduleRepository
                .findAll()
                .stream()
                .map(moduleMapper::mapModuleEntityToModule)
                .collect(Collectors.toList());
    }

    @Override
    public Module updateModule(Long moduleId, Module moduleToUpdate) {
        ModuleEntity moduleEntity = moduleRepository
                .findById(moduleId)
                .orElseThrow(() -> new EntityNotFoundException("Module with id: " + moduleId + " not found."));

        if (moduleToUpdate.getName() != null) {
            moduleEntity.setName(moduleToUpdate.getName());
        }

        if (moduleToUpdate.getBasePath() != null) {
            moduleEntity.setBasePath(moduleToUpdate.getBasePath());
        }

        ModuleEntity savedModuleEntity = moduleRepository.save(moduleEntity);

        return moduleMapper.mapModuleEntityToModule(savedModuleEntity);
    }
}
