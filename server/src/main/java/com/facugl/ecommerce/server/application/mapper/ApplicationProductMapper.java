package com.facugl.ecommerce.server.application.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;

import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;

@Mapper(componentModel = "spring", uses = CategoryOutputPort.class)
public interface ApplicationProductMapper {

    default Product mapToProduct(ProductRequest request, CategoryOutputPort categoryOutputPort) {
        Optional<Category> categoryOptional = categoryOutputPort.findCategoryByName(request.getCategoryName());

        if (categoryOptional.isPresent()) {
            return Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .image(request.getImage())
                    .status(request.getStatus())
                    .category(categoryOptional.get())
                    .build();
        }

        return Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .image(request.getImage())
                    .status(request.getStatus())
                    .build();
    }

    ProductResponse mapToProductResponse(Product product);

}
