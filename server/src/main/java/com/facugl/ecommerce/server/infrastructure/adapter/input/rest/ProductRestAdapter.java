package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.ApplicationProductMapper;
import com.facugl.ecommerce.server.application.port.input.products.ActiveProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.CreateProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.DeleteProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.GetAllProductsUseCase;
import com.facugl.ecommerce.server.application.port.input.products.GetProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.UpdateProductUseCase;
import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WebAdapter
@RestController
@RequestMapping("/products")
public class ProductRestAdapter {
        private final ApplicationProductMapper productMapper;

        private final CreateProductUseCase createProductUseCase;
        private final GetProductUseCase getProductUseCase;
        private final GetAllProductsUseCase getAllProductsUseCase;
        private final DeleteProductUseCase deleteProductUseCase;
        private final UpdateProductUseCase updateProductUseCase;
        private final ActiveProductUseCase activeProductUseCase;

        private final CategoryOutputPort categoryOutputPort;

        @PostMapping
        public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productToCreate) {
                Product product = productMapper.mapToProduct(productToCreate, categoryOutputPort);

                Product createdProduct = createProductUseCase.createProduct(product);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(productMapper.mapToProductResponse(createdProduct));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> getOneProduct(@PathVariable Long id) {
                Product product = getProductUseCase.getProductById(id);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(productMapper.mapToProductResponse(product));
        }

        @GetMapping
        public ResponseEntity<List<ProductResponse>> getAllProducts() {
                List<ProductResponse> productsResponsesList = getAllProductsUseCase.getAllProducts()
                                .stream()
                                .map(productMapper::mapToProductResponse)
                                .collect(Collectors.toList());

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(productsResponsesList);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
                deleteProductUseCase.deleteProductById(id);

                return ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                        @RequestBody @Valid ProductRequest productToUpdate) {
                Product product = productMapper.mapToProduct(productToUpdate, categoryOutputPort);

                Product updatedProduct = updateProductUseCase.updateProduct(id, product);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(productMapper.mapToProductResponse(updatedProduct));
        }

        @PatchMapping("/{id}/active")
        public ResponseEntity<Void> activeProduct(@PathVariable Long id, @RequestBody ProductStatus status) {
                activeProductUseCase.activeProduct(id, status);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .build();
        }

}
