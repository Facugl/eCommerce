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

import com.facugl.ecommerce.server.application.mapper.products.ApplicationProductDetailMapper;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.CreateProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.DeleteProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.GetAllProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.GetProductDetailUseCase;
import com.facugl.ecommerce.server.application.port.input.products.productsDetails.UpdateProductDetailUseCase;
import com.facugl.ecommerce.server.application.service.products.ProductDetailService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.products.ProductDetail;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.ProductDetailRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductDetailResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.products.CreateProductDetailValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/products-details")
@RestController
@WebAdapter
public class ProductDetailRestAdapter {
	private final ApplicationProductDetailMapper productDetailMapper;
	private final CreateProductDetailUseCase createProductDetailUseCase;
	private final GetProductDetailUseCase getProductDetailUseCase;
	private final GetAllProductDetailUseCase getAllProductDetailUseCase;
	private final UpdateProductDetailUseCase updateProductDetailUseCase;
	private final DeleteProductDetailUseCase deleteProductDetailUseCase;
	private final ProductDetailService productDetailService;

	@PostMapping
	public ResponseEntity<ProductDetailResponse> createProductDetail(
			@Validated(CreateProductDetailValidationGroup.class) @RequestBody ProductDetailRequest productDetailToCreate) {
		ProductDetail productDetail = productDetailService
				.mapProductDetailRequestToProductDetail(productDetailToCreate);

		ProductDetail createdProductDetail = createProductDetailUseCase.createProductDetail(productDetail);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productDetailMapper
						.mapProductDetailToProductDetailResponse(createdProductDetail));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDetailResponse> getOneProductDetail(@PathVariable Long id) {
		ProductDetail productDetail = getProductDetailUseCase.getProductDetailById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productDetailMapper.mapProductDetailToProductDetailResponse(productDetail));
	}

	@GetMapping
	public ResponseEntity<List<ProductDetailResponse>> getAllProductDetails() {
		List<ProductDetailResponse> productsDetails = getAllProductDetailUseCase.getAllProductsDetails()
				.stream()
				.map(productDetailMapper::mapProductDetailToProductDetailResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productsDetails);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDetailResponse> updateProductDetail(
			@PathVariable Long id,
			@Validated(UpdateProductDetailUseCase.class) @RequestBody ProductDetailRequest productDetailToUpdate) {
		ProductDetail productDetail = productDetailService
				.mapProductDetailRequestToProductDetail(productDetailToUpdate);

		ProductDetail updatedProductDetail = updateProductDetailUseCase.updateProductDetail(id, productDetail);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productDetailMapper
						.mapProductDetailToProductDetailResponse(updatedProductDetail));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductDetail(@PathVariable Long id) {
		deleteProductDetailUseCase.deleteProductDetailById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
