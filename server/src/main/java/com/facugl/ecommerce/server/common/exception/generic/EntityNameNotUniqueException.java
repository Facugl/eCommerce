package com.facugl.ecommerce.server.common.exception.generic;

public class EntityNameNotUniqueException extends RuntimeException {
    public EntityNameNotUniqueException(String message) {
        super(message);
    }
}
