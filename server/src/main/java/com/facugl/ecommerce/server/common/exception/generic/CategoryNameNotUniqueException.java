package com.facugl.ecommerce.server.common.exception.generic;

public class CategoryNameNotUniqueException extends RuntimeException {
    public CategoryNameNotUniqueException(String message) {
        super(message);
    }
}
