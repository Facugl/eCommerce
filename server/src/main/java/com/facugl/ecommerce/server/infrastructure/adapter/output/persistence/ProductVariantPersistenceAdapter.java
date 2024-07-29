package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.products.ProductVariantOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.products.ProductVariant;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.ImageDuplicateException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductVariantEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.products.PersistenceProductVariantMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.products.ProductRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.products.ProductVariantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ProductVariantPersistenceAdapter implements ProductVariantOutputPort {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final PersistenceProductVariantMapper productVariantMapper;

    @Override
    public ProductVariant createProductVariant(ProductVariant productVariantToCreate) {
        Long productId = productVariantToCreate.getProduct().getId();

        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));

        List<ProductVariantEntity> productsVariantsEntities = productVariantRepository
                .findProductsVariantsByProduct(productId);

        ProductVariantEntity productVariantEntity = productVariantMapper
                .mapProductVariantToProductVariantEntity(productVariantToCreate);

        if (!productsVariantsEntities.contains(productVariantEntity)) {
            productVariantEntity.setProduct(productEntity);

            ProductVariantEntity savedProductVariant = productVariantRepository.save(productVariantEntity);

            return productVariantMapper.mapProductVariantEntityToProductVariant(savedProductVariant);
        } else {
            throw new EntityAlreadyExistsException(
                    "Product variant with id: " + productVariantEntity.getId() + " already exists in product with id: "
                            + productVariantEntity.getProduct().getId());
        }
    }

    @Override
    public ProductVariant findProductVariantById(Long id) {
        return productVariantRepository
                .findProductVariantById(id)
                .map(productVariantMapper::mapProductVariantEntityToProductVariant)
                .orElseThrow(() -> new EntityNotFoundException("Product variant with id: " + id + " not found."));
    }

    @Override
    public List<ProductVariant> getAllProductsVariants() {
        return productVariantRepository
                .findAllProductVariants()
                .stream()
                .map(productVariantMapper::mapProductVariantEntityToProductVariant)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVariant> getAllProductsVariantsByProduct(Long productId) {
        List<ProductVariantEntity> productVariantEntities = productVariantRepository
                .findProductsVariantsByProduct(productId);

        return productVariantEntities
                .stream()
                .map(productVariantMapper::mapProductVariantEntityToProductVariant)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVariant> getAllProductsVariantsByVariantValue(Long variantValueId) {
        List<ProductVariantEntity> productVariantEntities = productVariantRepository
                .findProductsVariantsByVariantValue(variantValueId);

        return productVariantEntities
                .stream()
                .map(productVariantMapper::mapProductVariantEntityToProductVariant)
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariant updateProductVariant(Long id, ProductVariant productVariantToUpdate) {
        ProductVariantEntity productVariantEntity = productVariantRepository
                .findProductVariantById(id)
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
                    throw new ImageDuplicateException("The image URL already exists.");
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

        ProductVariantEntity savedProductVariantEntity = productVariantRepository.save(productVariantEntity);

        return productVariantMapper.mapProductVariantEntityToProductVariant(savedProductVariantEntity);
    }

    @Override
    public void deleteProductVariantById(Long id) {
        ProductVariantEntity productVariantEntity = productVariantRepository
                .findProductVariantById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product variant with id: " + id + " not found."));

        productVariantRepository.delete(productVariantEntity);
    }
}
