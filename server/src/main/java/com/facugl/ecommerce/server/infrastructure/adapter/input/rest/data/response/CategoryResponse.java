package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response;

import java.io.Serializable;

import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse implements Serializable {

    private Long id;
    private String name;
    private CategoryStatus status;
    private CategoryResponse parentCategory;

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", parentCategory='" + parentCategory +
                "}";
    }

}
