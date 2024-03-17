package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.entity.products.ProductEntity;
import com.facugl.ecommerce.server.infrastructure.adapter.output.persistence.mapper.PersistenceCategoryMapper;
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
    private final PersistenceCategoryMapper categoryMapper;

    @Override
    public boolean isProductNameUnique(String name) {
        return !productRepository.findByName(name).isPresent();
    }

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productMapper.mapToProductEntity(product, categoryMapper, categoryRepository);

        productEntity.setStatus(ProductStatus.ENABLED);

        ProductEntity createdProduct = productRepository.save(productEntity);

        return productMapper.mapToProduct(createdProduct, categoryMapper);
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> productMapper.mapToProduct(product, categoryMapper));
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productsEntities = productRepository.findAll();

        List<Product> products = productsEntities
                .stream()
                .map(product -> productMapper.mapToProduct(product, categoryMapper))
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        }
    }

    @Override
    public Product updateProduct(Long id, Product productToUpdate) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        if (productToUpdate.getName() != null) {
            productEntity.setName(productToUpdate.getName());
        }

        if (productToUpdate.getDescription() != null) {
            productEntity.setDescription(productToUpdate.getDescription());
        }

        if (productToUpdate.getImage() != null) {
            productEntity.setImage(productToUpdate.getImage());
        }

        if (productToUpdate.getCategory() != null) {
            productEntity
                    .setCategory(categoryMapper.mapToCategoryEntity(productToUpdate.getCategory()));
        }

        ProductEntity savedProductEntity = productRepository.save(productEntity);

        return productMapper.mapToProduct(savedProductEntity, categoryMapper);
    }

    @Override
    public void activeProduct(Long id, ProductStatus status) {
        Optional<ProductEntity> productEntityOptinal = productRepository.findById(id);

        if (productEntityOptinal.isPresent()) {
            ProductEntity productEntity = productEntityOptinal.get();

            productEntity.setStatus(status);

            productRepository.save(productEntity);
        }        
    }

}
