package com.facugl.ecommerce.server.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import com.facugl.ecommerce.server.application.port.output.ProductOutputPort;
import com.facugl.ecommerce.server.common.PersistenceAdapter;
import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.common.exception.generic.ImageDuplicateException;
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
        Long categoryId = productToCreate.getCategory().getId();

        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id: " + categoryId + " not found."));

        List<ProductEntity> productEntities = productRepository.findProductsByCategoryId(categoryId);

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
                .findProductWithCategoryById(id)
                .map(productEntity -> productMapper.mapProductEntityToProduct(productEntity))
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAllProductsWithCategory();

        return productEntityList
                .stream()
                .map(product -> productMapper.mapProductEntityToProduct(product))
                .collect(Collectors.toList());
    }

	@Override
	public List<Product> getAllProductsByCategory(Long categoryId) {
		List<ProductEntity> productEntities = productRepository.findProductsByCategoryId(categoryId);

		return productEntities
				.stream()
				.map(productEntity -> productMapper.mapProductEntityToProduct(productEntity))
				.collect(Collectors.toList());
	}

    @Override
    public void deleteProductById(Long id) {
        ProductEntity productEntity = productRepository
                .findProductWithCategoryById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        productRepository.delete(productEntity);
    }

    @Override
    public Product updateProduct(Long id, Product productToUpdate) {
        ProductEntity productEntity = productRepository
                .findProductWithCategoryById(id)
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
    public void activeProduct(Long id, ProductStatus status) {
        ProductEntity productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found."));

        productEntity.setStatus(status);

        productRepository.save(productEntity);
    }

}
