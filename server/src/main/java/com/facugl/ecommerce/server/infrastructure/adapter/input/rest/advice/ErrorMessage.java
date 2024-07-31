package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String backendMessage;
    private String message;
    private String method;
    private String url;
    private String timestamp;
}
