package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic;

public class ImageDuplicateException extends RuntimeException {
    public ImageDuplicateException(String message) {
        super(message);
    }
}
