package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic;

public class EntityNameNotUniqueException extends RuntimeException {
    public EntityNameNotUniqueException(String message) {
        super(message);
    }
}
