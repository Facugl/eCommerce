package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {

    };

    public InvalidEmailException(String message) {
        super(message);
    }
}
