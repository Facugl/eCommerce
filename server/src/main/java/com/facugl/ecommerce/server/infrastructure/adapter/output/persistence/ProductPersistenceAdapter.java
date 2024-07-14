package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.ImageDuplicateException;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.categories.CategoryEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceProductMapper;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.CategoryRepository;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class ProductPersistenceAdapter implements ProductOutputPort {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PersistenceProductMapper productMapper;

    @Override
    public Product createProduct(Product productToCreate) {
        Long categoryId = productToCreate.getCategory().getId();

        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " not found."));

        List<ProductEntity> products = productRepository.findProductsByCategoryId(categoryId);

        ProductEntity productEntity = productMapper.mapProductToProductEntity(productToCreate);

        if (!products.contains(productEntity)) {
            productEntity.setCategory(categoryEntity);

            ProductEntity createdProduct = productRepository.save(productEntity);

            return productMapper.mapProductEntityToProduct(createdProduct);
        } else {
            throw new EntityAlreadyExistsException(
                    "Product with id: " + productEntity.getId() + " already exists in category with id: " + categoryId);
        }
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository
                .findProductWithCategoryById(productId)
                .map(productMapper::mapProductEntityToProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllProductsWithCategory()
                .stream()
                .map(productMapper::mapProductEntityToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId)
                .stream()
                .map(productMapper::mapProductEntityToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(Long productId) {
        ProductEntity productEntity = productRepository
                .findProductWithCategoryById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));

        productRepository.delete(productEntity);
    }

    @Override
    public Product updateProduct(Long productId, Product productToUpdate) {
        ProductEntity productEntity = productRepository
                .findProductWithCategoryById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));

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
                    throw new ImageDuplicateException("The image URL: " + image + " already exists in the list.");
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
    public void activeProduct(Long productId, ProductStatus status) {
        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " not found."));

        productEntity.setStatus(status);

        productRepository.save(productEntity);
    }
}
