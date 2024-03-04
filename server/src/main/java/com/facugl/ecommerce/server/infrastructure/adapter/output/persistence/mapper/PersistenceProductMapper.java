package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;

@Mapper(componentModel = "spring")
public interface PersistenceProductMapper {

    @Mapping(target = "id", ignore = true)
    default ProductEntity mapToProductEntity(
            Product product,
            PersistenceCategoryMapper categoryMapper,
            CategoryRepository categoryRepository) {
        return ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .status(product.getStatus())
                .category(categoryMapper.mapToCategoryEntity(product.getCategory(), categoryRepository))
                .build();
    }

    default Product mapToProduct(ProductEntity entity, PersistenceCategoryMapper categoryMapper) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .image(entity.getImage())
                .status(entity.getStatus())
                .category(categoryMapper.mapToCategory(entity.getCategory()))
                .build();
    }

}
