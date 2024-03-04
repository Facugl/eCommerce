package com.facugl.ecommerce.server.domain.model.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProductStatus {
    ENABLED,
    DISABLED;

    @JsonCreator
    public static ProductStatus fromJson(@JsonProperty("status") String status) {
        return valueOf(status);
    }
    
}
