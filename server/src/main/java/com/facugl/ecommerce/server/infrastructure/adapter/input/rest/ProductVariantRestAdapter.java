package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.products.ApplicationProductVariantMapper;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.CreateProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.DeleteProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.GetAllProductsVariantsUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.GetProductVariantUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsVariants.UpdateProductVariantUseCase;
import com.facugl.ecommerce.server.application.service.products.ProductVariantService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.products.ProductVariant;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductVariantRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductVariantResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.CreateProductVariantValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.UpdateProductVariantValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WebAdapter
@RestController
@RequestMapping("/products-variants")
public class ProductVariantRestAdapter {
	private final CreateProductVariantUseCase createProductVariantUseCase;
	private final GetProductVariantUseCase getProductVariantUseCase;
	private final GetAllProductsVariantsUseCase getAllProductVariantsUseCase;
	private final UpdateProductVariantUseCase updateProductVariantUseCase;
	private final DeleteProductVariantUseCase deleteProductVariantUseCase;
	private final ApplicationProductVariantMapper productVariantMapper;
	private final ProductVariantService productVariantService;

	@PostMapping
	public ResponseEntity<ProductVariantResponse> createProductVariant(
			@Validated(CreateProductVariantValidationGroup.class) @RequestBody ProductVariantRequest productVariantToCreate) {
		ProductVariant productVariant = productVariantService
				.mapProductVariantRequestToProductVariant(productVariantToCreate);

		ProductVariant createdProductVariant = createProductVariantUseCase.createProductVariant(productVariant);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productVariantMapper.mapProductVariantToProductVariantResponse(createdProductVariant));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductVariantResponse> getOneProductVariant(@PathVariable Long id) {
		ProductVariant productVariant = getProductVariantUseCase.getProductVariantById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productVariantMapper.mapProductVariantToProductVariantResponse(productVariant));
	}

	@GetMapping
	public ResponseEntity<List<ProductVariantResponse>> getAllProductsVariants() {
		List<ProductVariantResponse> productsVariants = getAllProductVariantsUseCase
				.getAllProductsVariants()
				.stream()
				.map(productVariantMapper::mapProductVariantToProductVariantResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productsVariants);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductVariantResponse> updateProductVariant(
			@PathVariable Long id,
			@Validated(UpdateProductVariantValidationGroup.class) @RequestBody ProductVariantRequest productVariantToUpdate) {
		ProductVariant productVariant = productVariantService
				.mapProductVariantRequestToProductVariant(productVariantToUpdate);

		ProductVariant updatedProductVariant = updateProductVariantUseCase.updateProductVariant(id,
				productVariant);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productVariantMapper
						.mapProductVariantToProductVariantResponse(updatedProductVariant));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductVariant(@PathVariable Long id) {
		deleteProductVariantUseCase.deleteProductVariantById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
