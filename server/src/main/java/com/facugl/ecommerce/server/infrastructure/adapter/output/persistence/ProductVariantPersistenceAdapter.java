package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.ProductVariantOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.common.exception.generic.ImageDuplicateException;
import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;
import com.facugl.ecommerce.server.domain.model.variantsValues.VariantValue;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.productsVariants.ProductVariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.variantsValues.VariantValueEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceProductVariantMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.ProductRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.ProductVariantRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.VariantValueRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ProductVariantPersistenceAdapter implements ProductVariantOutputPort {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final VariantValueRepository variantValueRepository;

    private final PersistenceProductVariantMapper productVariantMapper;

    @Override
    public ProductVariant createProductVariant(ProductVariant productVariantToCreate) {
        Long productId = productVariantToCreate.getProduct().getId();

        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));

        List<ProductVariantEntity> productVariantEntitySet = productEntity.getProductsVariants();

        ProductVariantEntity productVariantEntity = productVariantMapper
                .mapProductVariantToProductVariantEntity(productVariantToCreate);

        if (!productVariantEntitySet.contains(productVariantEntity)) {
            productVariantEntity.setProduct(productEntity);

            Set<VariantValueEntity> variantValueEntitySet = new HashSet<>();

            for (VariantValue variantValue : productVariantToCreate.getVariantsValues()) {
                VariantValueEntity variantValueEntity = variantValueRepository
                        .findById(variantValue.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Product with id: " + variantValue.getId() + " not found."));

                variantValueEntitySet.add(variantValueEntity);
            }

            productVariantEntity.setVariantsValues(variantValueEntitySet);

            ProductVariantEntity productVariantSaved = productVariantRepository.save(productVariantEntity);

            return productVariantMapper.mapProductVariantEntityToProductVariant(productVariantSaved);
        } else {
            throw new EntityAlreadyExistsException(
                    "Product variant with id: " + productVariantEntity.getId() + " already exists in product with id: "
                            + productVariantEntity.getProduct().getId());
        }
    }

    @Override
    public ProductVariant findProductVariantById(Long id) {
        return productVariantRepository
                .findById(id)
                .map(productVariantEntity -> productVariantMapper.mapProductVariantEntityToProductVariant(
                        productVariantEntity))
                .orElseThrow(() -> new EntityNotFoundException("Product variant with id: " + id + " not found."));
    }

    @Override
    public List<ProductVariant> getAllProductsVariants() {
        return productVariantRepository
                .findAll()
                .stream()
                .map(productVariantEntity -> productVariantMapper.mapProductVariantEntityToProductVariant(
                        productVariantEntity))
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariant updateProductVariant(Long id, ProductVariant productVariantToUpdate) {
        ProductVariantEntity productVariantEntity = productVariantRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product variant with id: " + id + " not found."));

        if (productVariantToUpdate.getDescription() != null) {
            productVariantEntity.setDescription(productVariantToUpdate.getDescription());
        }

        if (productVariantToUpdate.getImages() != null) {
            List<String> newImages = productVariantToUpdate.getImages();
            List<String> productImages = productVariantEntity.getImages();

            for (String image : newImages) {
                if (!productImages.contains(image)) {
                    productImages.add(image);
                } else {
                    throw new ImageDuplicateException("The image URL already exists in the list.");
                }
            }
        }

        if (productVariantToUpdate.getPrice() != null) {
            productVariantEntity.setPrice(productVariantToUpdate.getPrice());
        }

        if (productVariantToUpdate.getSku() != null) {
            productVariantEntity.setSku(productVariantToUpdate.getSku());
        }

        if (productVariantToUpdate.getStock() != null) {
            productVariantEntity.setStock(productVariantToUpdate.getStock());
        }

        if (productVariantToUpdate.getProduct() != null) {
            Long productId = productVariantToUpdate.getProduct().getId();

            ProductEntity productEntity = productRepository
                    .findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));

            productVariantEntity.setProduct(productEntity);
        }

        if (productVariantToUpdate.getVariantsValues() != null) {
            for (VariantValue variantValue : productVariantToUpdate.getVariantsValues()) {
                VariantValueEntity variantValueEntity = variantValueRepository
                        .findById(variantValue.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Variant value with id: " + variantValue.getId() + " not found."));

                productVariantEntity.getVariantsValues().add(variantValueEntity);
            }
        }

        ProductVariantEntity savedProductVariantEntity = productVariantRepository.save(productVariantEntity);

        return productVariantMapper.mapProductVariantEntityToProductVariant(savedProductVariantEntity);
    }

    @Override
    public void deleteProductVariantById(Long id) {
        ProductVariantEntity productVariantEntity = productVariantRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product variant with id: " + id + " not found."));

        productVariantEntity.getVariantsValues().clear();

        productVariantRepository.delete(productVariantEntity);
    }

}
