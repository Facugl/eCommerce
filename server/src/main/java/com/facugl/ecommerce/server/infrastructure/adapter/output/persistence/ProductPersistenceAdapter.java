package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityNameNotUniqueException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
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
        String productName = productToCreate.getName();
        String categoryName = productToCreate.getCategory().getName();

        CategoryEntity categoryEntity = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category with name: " + categoryName + " not found."));

        List<ProductEntity> productEntityList = productRepository.findByCategoryNameAndName(categoryName, productName);

        if (productEntityList.isEmpty()) {
            ProductEntity productEntity = productMapper.mapProductToProductEntity(productToCreate);

            productEntity.setCategory(categoryEntity);
            productEntity.setStatus(ProductStatus.ENABLED);

            ProductEntity createdProduct = productRepository.save(productEntity);

            return productMapper.mapProductEntityToProduct(createdProduct);
        } else {
            throw new EntityNameNotUniqueException(
                    "Product with name: " + productToCreate.getName() + " already exist.");
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

        List<Product> products = productEntityList
                .stream()
                .map(product -> productMapper.mapProductEntityToProduct(product))
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        List<ProductEntity> productEntityList = productRepository.findByCategoryId(categoryId);

        List<Product> productList = productEntityList
                .stream()
                .map(productEntity -> productMapper.mapProductEntityToProduct(productEntity))
                .collect(Collectors.toList());

        return productList;
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

        if (productEntity.getImage() != null) {
            productEntity.setImage(productToUpdate.getImage());
        }

        if (productToUpdate.getCategory() != null) {
            CategoryEntity categoryEntity = categoryRepository
                    .findByName(productToUpdate.getCategory().getName())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category with name: " + productToUpdate.getCategory().getName() + " not found."));

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
