package com.facugl.ecommerce.server.domain.model.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {
    private Long id;
    private String name;
    private String path;
    private String httpMethod;
    private boolean permitAll;
    private Module module;
}
