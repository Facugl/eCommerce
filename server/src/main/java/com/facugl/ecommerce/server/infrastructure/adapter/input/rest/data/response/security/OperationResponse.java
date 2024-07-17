package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationResponse implements Serializable {
    private Long id;
    private String name;
    private String path;
    private String httpMethod;
    private boolean permitAll;
    private ModuleResponse module;
}
