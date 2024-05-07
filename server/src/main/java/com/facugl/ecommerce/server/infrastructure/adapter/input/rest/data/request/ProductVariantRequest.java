package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request;

import java.math.BigDecimal;
import java.util.List;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.productsVariants.CreateProductVariantValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.productsVariants.UpdateProductVariantValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.CreateVariantValueValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.variantsValues.UpdateVariantValueValidationGroup;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {

        @NotBlank(message = "Description shouldn't be null or empty", groups = {
                        CreateProductVariantValidationGroup.class })
        @Size(max = 5000, message = "Description must be at most 5000 characters", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        private String description;

        @NotEmpty(message = "The list of images can not be empty", groups = { CreateProductVariantValidationGroup.class })
        private List<String> images;

        @NotNull(message = "Price shouldn't be null", groups = { CreateProductVariantValidationGroup.class })
        @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 digits with 2 decimal places", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        @Positive(message = "Price must be a positive value", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        @DecimalMin(value = "0.01", inclusive = false, message = "Price must be greater than 0", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        @DecimalMax(value = "99999999.99", message = "Price must be less than or equal to 99999999.99", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        private BigDecimal price;

        @NotBlank(message = "Sku shouldn't be null or empty", groups = { CreateProductVariantValidationGroup.class })
        @Size(min = 1, max = 20, message = "Sku must be between 1 and 20 characters", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        private String sku;

        @NotNull(message = "Stock can not be null", groups = { CreateProductVariantValidationGroup.class })
        @PositiveOrZero(message = "Stock must be a positive number or zero", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        private Integer stock;

        @NotNull(message = "Product ID can not be null", groups = {
                        CreateProductVariantValidationGroup.class })
        @Positive(message = "Product ID must be a positive value", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        @Min(value = 1, message = "Product ID must be greater than or equal to 1", groups = {
                        CreateProductVariantValidationGroup.class, UpdateProductVariantValidationGroup.class })
        private Long productId;

        @NotNull(message = "Variant value ID can not be null", groups = {
                        CreateVariantValueValidationGroup.class })
        @Positive(message = "Variant value ID must be a positive value", groups = {
                        CreateVariantValueValidationGroup.class, UpdateVariantValueValidationGroup.class })
        @Min(value = 1, message = "Variant value ID must be greater than or equal to 1", groups = {
                        CreateVariantValueValidationGroup.class, UpdateVariantValueValidationGroup.class })
        private Long variantValueId;

}
