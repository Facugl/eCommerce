package com.facugl.ecommerce.server.domain.model.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CategoryStatus {
    ENABLED,
    DISABLED;

    @JsonCreator
    public static CategoryStatus fromJson(@JsonProperty("status") String status) {
        return valueOf(status);
    }

}
