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

import com.facugl.ecommerce.server.application.mapper.security.ApplicationModuleMapper;
import com.facugl.ecommerce.server.application.port.input.security.modules.CreateModuleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.DeleteModuleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.GetAllModulesUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.GetModuleUseCase;
import com.facugl.ecommerce.server.application.port.input.security.modules.UpdateModuleUseCase;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.security.Module;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.security.ModuleRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security.ModuleResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.modules.CreateModuleValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.security.modules.UpdateModuleValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/modules")
@RestController
@WebAdapter
public class ModuleRestAdapter {
    private final ApplicationModuleMapper moduleMapper;
    private final CreateModuleUseCase createModuleUseCase;
    private final GetModuleUseCase getModuleUseCase;
    private final GetAllModulesUseCase getAllModulesUseCase;
    private final UpdateModuleUseCase updateModuleUseCase;
    private final DeleteModuleUseCase deleteModuleUseCase;

    @PostMapping
    public ResponseEntity<ModuleResponse> createModule(
            @Validated(CreateModuleValidationGroup.class) @RequestBody ModuleRequest moduleToCrete) {
        Module module = moduleMapper.mapModuleRequestToModule(moduleToCrete);

        Module createdModule = createModuleUseCase.createModule(module);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(moduleMapper.mapModuleToModuleResponse(createdModule));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponse> getOneModule(@PathVariable Long id) {
        Module module = getModuleUseCase.getModuleById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(moduleMapper.mapModuleToModuleResponse(module));
    }

    @GetMapping
    public ResponseEntity<List<ModuleResponse>> getAllModules() {
        List<ModuleResponse> moduleResponses = getAllModulesUseCase.getAllModules()
                .stream()
                .map(moduleMapper::mapModuleToModuleResponse)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(moduleResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleResponse> updateModule(
            @PathVariable Long id,
            @Validated(UpdateModuleValidationGroup.class) @RequestBody ModuleRequest moduleToUpdate) {
        Module module = moduleMapper.mapModuleRequestToModule(moduleToUpdate);

        Module updatedModule = updateModuleUseCase.updateModule(id, module);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(moduleMapper.mapModuleToModuleResponse(updatedModule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModuleById(@PathVariable Long id) {
        deleteModuleUseCase.deleteModuleById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
