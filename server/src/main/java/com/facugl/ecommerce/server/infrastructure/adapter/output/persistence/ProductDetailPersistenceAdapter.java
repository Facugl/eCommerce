package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.products.ProductDetailOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.products.ProductDetail;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductDetailEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductVariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.products.PersistenceProductDetailMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantValueRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.products.ProductDetailRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.products.ProductVariantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ProductDetailPersistenceAdapter implements ProductDetailOutputPort {
    private final ProductDetailRepository productDetailRepository;
    private final ProductVariantRepository productVariantRepository;
    private final VariantValueRepository variantValueRepository;
    private final PersistenceProductDetailMapper productDetailMapper;

    @Override
    public ProductDetail createProductDetail(ProductDetail productDetailToCreate) {
        ProductDetailEntity productDetailEntity = productDetailMapper
                .mapProductDetailToProductDetailEntity(productDetailToCreate);

        Long productVariantId = productDetailToCreate.getProductVariant().getId();
        ProductVariantEntity productVariantEntity = productVariantRepository
                .findById(productVariantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ProductVariant with id: " + productVariantId + " not found."));
        productDetailEntity.setProductVariant(productVariantEntity);

        Long variantValueId = productDetailToCreate.getVariantValue().getId();
        VariantValueEntity variantValueEntity = variantValueRepository
                .findById(variantValueId)
                .orElseThrow(
                        () -> new EntityNotFoundException("VariantValue with id: " + variantValueId + " not found."));
        productDetailEntity.setVariantValue(variantValueEntity);

        ProductDetailEntity savedProductDetailEntity = productDetailRepository.save(productDetailEntity);

        return productDetailMapper.mapProductDetailEntityToProductDetail(savedProductDetailEntity);
    }

    @Override
    public void deleteProductDetailById(Long id) {
        ProductDetailEntity productDetailEntity = productDetailRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product detail with id: " + id + " not found."));

        productDetailRepository.delete(productDetailEntity);
    }

    @Override
    public ProductDetail findProductDetailById(Long id) {
        return productDetailRepository
                .findById(id)
                .map(productDetailMapper::mapProductDetailEntityToProductDetail)
                .orElseThrow(() -> new EntityNotFoundException("Product detail with id: " + id + " not found."));
    }

    @Override
    public List<ProductDetail> getAllProductDetail() {
        return productDetailRepository
                .findAll()
                .stream()
                .map(productDetailMapper::mapProductDetailEntityToProductDetail)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetail updateProductDetail(Long id, ProductDetail productDetailToUpdate) {
        ProductDetailEntity productDetailEntity = productDetailRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product detail with id: " + id + " not found."));

        if (productDetailToUpdate.getProductVariant() != null) {
            Long productVariantId = productDetailToUpdate.getProductVariant().getId();

            ProductVariantEntity productVariantEntity = productVariantRepository
                    .findById(productVariantId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Product variant with id: " + productVariantId + " not found"));

            productDetailEntity.setProductVariant(productVariantEntity);
        }

        if (productDetailToUpdate.getVariantValue() != null) {
            Long variantValueId = productDetailToUpdate.getVariantValue().getId();

            VariantValueEntity variantValueEntity = variantValueRepository
                    .findById(variantValueId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Variant value with id: " + variantValueId + " not found."));

            productDetailEntity.setVariantValue(variantValueEntity);
        }

        return productDetailMapper.mapProductDetailEntityToProductDetail(productDetailEntity);
    }
}
