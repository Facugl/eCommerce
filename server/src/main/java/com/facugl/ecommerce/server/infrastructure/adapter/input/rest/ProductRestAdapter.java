package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.products.ApplicationProductMapper;
import com.facugl.ecommerce.server.application.mapper.products.ApplicationProductVariantMapper;
import com.facugl.ecommerce.server.application.port.input.products.products.ActiveProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.products.CreateProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.products.DeleteProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.products.GetAllProductsUseCase;
import com.facugl.ecommerce.server.application.port.input.products.products.GetProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.products.UpdateProductUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.GetAllProductsVariantsByProductUseCase;
import com.facugl.ecommerce.server.application.service.products.ProductService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.products.ProductStatus;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductVariantResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.CreateProductValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.UpdateProductValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WebAdapter
@RestController
@RequestMapping("/products")
public class ProductRestAdapter {
	private final ApplicationProductMapper productMapper;
	private final ApplicationProductVariantMapper productVariantMapper;
	private final CreateProductUseCase createProductUseCase;
	private final GetProductUseCase getProductUseCase;
	private final GetAllProductsUseCase getAllProductsUseCase;
	private final GetAllProductsVariantsByProductUseCase getAllProductsVariantsByProductUseCase;
	private final DeleteProductUseCase deleteProductUseCase;
	private final UpdateProductUseCase updateProductUseCase;
	private final ActiveProductUseCase activeProductUseCase;
	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(
			@Validated(CreateProductValidationGroup.class) @RequestBody ProductRequest productToCreate) {
		Product product = productService.mapProductRequestToProduct(productToCreate);

		Product createdProduct = createProductUseCase.createProduct(product);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productMapper.mapProductToProductResponse(createdProduct));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getOneProduct(@PathVariable Long id) {
		Product product = getProductUseCase.getProductById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productMapper.mapProductToProductResponse(product));
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> products = getAllProductsUseCase.getAllProducts()
				.stream()
				.map(productMapper::mapProductToProductResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(products);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
		deleteProductUseCase.deleteProductById(id);

		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable Long id,
			@Validated(UpdateProductValidationGroup.class) @RequestBody ProductRequest productToUpdate) {
		Product product = productService.mapProductRequestToProduct(productToUpdate);

		Product updatedProduct = updateProductUseCase.updateProduct(id, product);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productMapper.mapProductToProductResponse(updatedProduct));
	}

	@PatchMapping("/{id}/active")
	public ResponseEntity<Void> activeProduct(@PathVariable Long id, @RequestBody ProductStatus status) {
		activeProductUseCase.activeProduct(id, status);

		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}

	@GetMapping("/{id}/productsVariants")
	public ResponseEntity<List<ProductVariantResponse>> getAllProductsVariants(@PathVariable Long id) {
		List<ProductVariantResponse> productsVariants = getAllProductsVariantsByProductUseCase
				.getAllProductsVariantsByProduct(id)
				.stream()
				.map(productVariantMapper::mapProductVariantToProductVariantResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productsVariants);
	}
}
