package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.common.exception.generic.ImageDuplicateException;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.domain.model.productsVariants.ProductVariant;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceProductMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceProductVariantMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ProductPersistenceAdapter implements ProductOutputPort {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final PersistenceProductMapper productMapper;
    private final PersistenceProductVariantMapper productVariantMapper;

    @Override
    public Product createProduct(Product productToCreate) {
        Long categoryId = productToCreate.getCategory().getId();

        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " not found."));

        Set<ProductEntity> productEntities = categoryEntity.getProducts();

        ProductEntity productEntity = productMapper.mapProductToProductEntity(productToCreate);

        if (!productEntities.contains(productEntity)) {
            productEntity.setCategory(categoryEntity);

            productEntity.setStatus(ProductStatus.ENABLED);

            ProductEntity createdProduct = productRepository.save(productEntity);

            return productMapper.mapProductEntityToProduct(createdProduct);
        } else {
            throw new EntityAlreadyExistsException(
                    "Product with id: " + productEntity.getId() + " already exists in category with id: " + categoryId);
        }
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository
                .findById(id)
                .map(productEntity -> productMapper.mapProductEntityToProduct(productEntity))
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();

        return productEntityList
                .stream()
                .map(product -> productMapper.mapProductEntityToProduct(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVariant> getAllProductsVariantsByProduct(Long productId) {
        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));

        return productEntity
                .getProductsVariants()
                .stream()
                .map(productVariantEntity -> productVariantMapper.mapProductVariantEntityToProductVariant(
                        productVariantEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(Long id) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        productRepository.delete(productEntity);
    }

    @Override
    public Product updateProduct(Long id, Product productToUpdate) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        if (productToUpdate.getName() != null) {
            productEntity.setName(productToUpdate.getName());
        }

        if (productToUpdate.getDescription() != null) {
            productEntity.setDescription(productToUpdate.getDescription());
        }

        if (productToUpdate.getImages() != null) {
            List<String> newImages = productToUpdate.getImages();
            List<String> productImages = productEntity.getImages();

            for (String image : newImages) {
                if (!productImages.contains(image)) {
                    productImages.add(image);
                } else {
                    throw new ImageDuplicateException("The image URL already exists in the list.");
                }
            }
        }

        if (productToUpdate.getCategory() != null) {
            Long categoryId = productToUpdate.getCategory().getId();

            CategoryEntity categoryEntity = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category with id: " + categoryId + " not found."));

            productEntity.setCategory(categoryEntity);
        }

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        return productMapper.mapProductEntityToProduct(savedProductEntity);
    }

    @Override
    public void activeProduct(Long id, ProductStatus status) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        productEntity.setStatus(status);

        productRepository.save(productEntity);
    }

}
