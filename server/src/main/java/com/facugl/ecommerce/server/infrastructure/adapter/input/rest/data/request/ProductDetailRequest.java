package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import java.io.Serializable;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.CreateProductDetailValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.UpdateProductDetailValidationGroup;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest implements Serializable {

    @NotNull(message = "productVariantId can not be null.", groups = { CreateProductDetailValidationGroup.class })
    @Positive(message = "productVariantId must be a positive number.", groups = {
            CreateProductDetailValidationGroup.class, UpdateProductDetailValidationGroup.class })
    private Long productVariantId;

    @NotNull(message = "variantValueId can not be null.", groups = { CreateProductDetailValidationGroup.class })
    @Positive(message = "variantValueId must be a positive number.", groups = {
            CreateProductDetailValidationGroup.class, UpdateProductDetailValidationGroup.class })
    private Long variantValueId;

}
