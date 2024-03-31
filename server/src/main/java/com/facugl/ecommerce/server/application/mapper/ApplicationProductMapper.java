package com.facugl.ecommerce.server.application.mapper;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.application.service.CategoryService;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.Product.ProductBuilder;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;

@Mapper(componentModel = "spring")
public interface ApplicationProductMapper {

    default Product mapProductRequestToProduct(ProductRequest request, CategoryService categoryService) {
        ProductBuilder productBuilder = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .status(request.getStatus());

        if (request.getCategoryName() != null) {
            Category parentCategory = categoryService
                    .getCategoryByName(request.getCategoryName())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category with name: " + request.getCategoryName() + " not found."));

            productBuilder.category(parentCategory);
        }

        return productBuilder.build();
    }

    ProductResponse mapToProductResponse(Product product);

}
